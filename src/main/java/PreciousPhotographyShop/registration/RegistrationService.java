package PreciousPhotographyShop.registration;

import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserService;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {

    RegistrationService(UserService userService, EmailValidator emailValidator){
        this.userService = userService;
        this.emailValidator = emailValidator;
    }

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid!");
        }
        return userService.signUpUser(new UserEntity(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        ));
    }
}
