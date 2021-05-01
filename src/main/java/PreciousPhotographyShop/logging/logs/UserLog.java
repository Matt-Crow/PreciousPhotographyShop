package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.users.UserEntity;

/**
 *
 * @author Matt
 */
public class UserLog extends AbstractLog {
    private final UserEntity forUser;
    
    public UserLog(UserEntity forUser){
        this.forUser = forUser;
    }

    @Override
    protected String getLogSubfolderName() {
        return "users";
    }

    @Override
    protected String getLogFileName() {
        return String.format("%s.txt", forUser.getId());
    }

    @Override
    protected String format(String eventString) {
        return eventString;
    }
}
