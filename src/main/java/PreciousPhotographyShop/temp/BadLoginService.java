package PreciousPhotographyShop.temp;

import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class BadLoginService {
    @Autowired
    private UserRepository repo;
    
    private UserEntity loggedInUser;
    
    public BadLoginService(){
        
    }
    
    public final UserEntity getLoggedInUser(){
        if(loggedInUser == null){
            // throwing NPE when in ctor, probably b/c repo isn't autowired yet
            loggedInUser = repo.findUserByEmail("fakey@aol.com").orElse(null);
            if(loggedInUser == null){
                loggedInUser = new UserEntity();
                loggedInUser.setEmail("fakey@aol.com");
                loggedInUser.setPassword("12345");
                loggedInUser.setUsername("Fakey Mc Dontexist");
                loggedInUser = repo.save(loggedInUser);
            }
        }
        return loggedInUser;
    }
}
