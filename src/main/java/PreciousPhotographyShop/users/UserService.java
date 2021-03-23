package PreciousPhotographyShop.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
