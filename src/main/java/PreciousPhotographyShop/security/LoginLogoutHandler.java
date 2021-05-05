package PreciousPhotographyShop.security;

import PreciousPhotographyShop.logging.LogService;
import PreciousPhotographyShop.logging.events.LoginEvent;
import PreciousPhotographyShop.logging.events.LogoutEvent;
import PreciousPhotographyShop.temp.LoginService;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * Captures login and logout traffic in WebSecurityConfig
 * 
 * @author Matt Crow
 */
@Component
public class LoginLogoutHandler implements AuthenticationSuccessHandler, LogoutHandler {
    @Autowired LogService logService;
    @Autowired LoginService loginService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        LoginEvent evt = new LoginEvent(
            loginService.getLoggedInUser(),
            LocalDateTime.now(),
            request.getRemoteAddr()
        );
        logService.logEvent(evt);
        
        response.sendRedirect(request.getContextPath()); // continue the request as normal
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication a) {
        LogoutEvent evt = new LogoutEvent(
            loginService.getLoggedInUser(),
            LocalDateTime.now(),
            request.getRemoteAddr()
        );
        logService.logEvent(evt);
        
        try {
            response.sendRedirect(request.getContextPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
