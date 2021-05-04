package PreciousPhotographyShop.index;

import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class UserMatcher extends AbstractMatcher<UserEntity> {
    @Autowired private UserRepository users;
    
    @Override
    protected Set<UserEntity> findMatches(String term) {
        Set<UserEntity> matches = new HashSet<>();
        users.findAllByUsernameContainingIgnoreCase(term).forEach((user)->{
            matches.add(user);
        });
        return matches;
    }

}
