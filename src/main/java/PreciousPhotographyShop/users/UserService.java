package PreciousPhotographyShop.users;

import PreciousPhotographyShop.registration.token.ConfirmationTokenService;
import PreciousPhotographyShop.registration.token.ConfirmationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Daniel V
 * Contains all the business logic within the HTTP calls in
 * @UserController + login verification and authentication
 */

@Service
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "Could not find an account associated with this email";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    
    
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public UserEntity getSingleUser(String email) {
        UserEntity user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalStateException((
                "User with id " + email + " cannot be found"
                )));
        return user;
    }

    /**
     * This is an @GET HTTP call
     * @return Returns a list of all user by name and email
     */
    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    /**
     * This is an @POST HTTP call, additionally it checks to see if
     * the email is taken
     * @param user The user to be stored in the database
     */
    public void addNewUser(UserEntity user) {
        Optional<UserEntity> UserOptional = userRepository.findUserByEmail(user.getEmail());
        if(UserOptional.isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        userRepository.save(user);
    }

    /**
     * This is an @DELETE HTTP call, it deletes a user in the database by id
     * @param id The id of the user that will be deleted
     */
    public void deleteUser(String id) {
        boolean exists = userRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Student doesn't exist");
        }
        userRepository.deleteById(id);
    }
    
    public void updateUser(String id, SellerPageInfo newInfo) throws Exception {
        UserEntity user = userRepository.findById(id).get();
        user.setUsername(newInfo.getUsername());
        user.setDescription(newInfo.getDescription());
        if(newInfo.isProfilePictureIdSet()){
            user.setProfilePictureId(newInfo.getProfilePictureId());
        }
        if(newInfo.isPasswordSet()){
            //user.setPassword(bCryptPasswordEncoder.encode(newInfo.getPassword()));
        }
        userRepository.save(user); // update the user in the database
    }
    
    /**
     * This is an @PUT HTTP call that updates either the name, email, or both entries on a user.
     * @param id The id for searching in the Database
     * @param password The password that potentially will be updated
     * @param email The email that potentially will be updated
     * @param username The username that potentially will be updated
     */
    @Transactional
    public void updateUser(String id, String password,
                           String email, String username){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with id " + id + " does not exist"
        ));

        if(password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)){
            user.setPassword(password);
        }

        if(username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username)){
            user.setUsername(username);
        }

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<UserEntity> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)
                ));
    }

    public String signUpUser(UserEntity userEntity){
        boolean userExists = userRepository.findUserByEmail(userEntity.getEmail()).isPresent();

        if(userExists){
            throw new IllegalStateException("Email is already taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(userEntity.getPassword());

        userEntity.setPassword(encodedPassword);

        userRepository.save(userEntity);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEntity
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: Send email
        return token;
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public final UserEntity getLoggedInUser(){

        UserEntity e = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()){
            Object p = auth.getPrincipal();
            e = (UserEntity)p;
        }
        return e;
    }

}
