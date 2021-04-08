package PreciousPhotographyShop.registration;

import PreciousPhotographyShop.registration.token.ConfirmationToken;
import PreciousPhotographyShop.registration.token.ConfirmationTokenService;
import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class RegistrationService {

    RegistrationService(UserService userService,
                        EmailValidator emailValidator,
                        ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
    }

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

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

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.
                getToken(token)
                .orElseThrow(() -> new IllegalStateException(("Token not found")));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUserEntity().getEmail()
        );
        return "Confirmed";
    }
}
