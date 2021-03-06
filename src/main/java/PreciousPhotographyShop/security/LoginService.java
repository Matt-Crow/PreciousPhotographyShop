package PreciousPhotographyShop.security;

import PreciousPhotographyShop.users.UserEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class LoginService {
    /**
     * 
     * @return the currently logged in user, or null if no user is logged in. 
     */
    public final UserEntity getLoggedInUser(){
        UserEntity e = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()){
            Object d = auth.getPrincipal();
            e = (UserEntity)d;
        }
        return e;
    }
}
