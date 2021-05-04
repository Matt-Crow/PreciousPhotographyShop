package PreciousPhotographyShop.security;

import PreciousPhotographyShop.logging.LogService;
import PreciousPhotographyShop.logging.events.LoginEvent;
import PreciousPhotographyShop.temp.LoginService;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * This was the only way I could think of to capture login / logout traffic.
 * There is probably a better solution
 * @author Matt Crow
 */
@Component
public class LoginLogoutHandler implements AuthenticationSuccessHandler {
    @Autowired LogService logService;
    @Autowired LoginService loginService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) throws IOException, ServletException {
        LoginEvent evt = new LoginEvent(
            loginService.getLoggedInUser(),
            LocalDateTime.now(),
            hsr.getRemoteUser()
        );
        logService.logEvent(evt);
    }
    
}
