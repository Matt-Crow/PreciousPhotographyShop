package PreciousPhotographyShop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addNewUser(User user) {
        Optional<User> UserOptional = userRepository.findUserByEmail(user.getEmail());
        if(UserOptional.isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        boolean exists = userRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("Student doesn't exist");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(String id, String name, String email){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "User with id " + id + " does not exist"
        ));

        if(name != null && name.length() > 0 && !Objects.equals(user.getName(), name)){
            user.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            Optional<User> userOptional = userRepository.findUserByEmail(email);
            if(userOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
        }
    }
}
