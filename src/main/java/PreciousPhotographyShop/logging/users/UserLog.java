package PreciousPhotographyShop.logging.users;

import PreciousPhotographyShop.logging.AbstractLog;
import PreciousPhotographyShop.users.UserEntity;

/**
 *
 * @author Matt
 */
public class UserLog extends AbstractLog {
    private final UserEntity forUser;
    
    public UserLog(String path, UserEntity forUser){
        super(path);
        this.forUser = forUser;
    }

    @Override
    protected String format(String eventString) {
        return eventString;
    }
}
