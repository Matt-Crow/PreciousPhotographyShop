package PreciousPhotographyShop.logging.events;

import PreciousPhotographyShop.users.UserEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Matt
 */
public class LoginEvent implements AbstractLoggedEvent {
    private final UserEntity user;
    private final LocalDateTime time;
    private final String ip;
    
    public LoginEvent(UserEntity user, LocalDateTime time, String ip){
        this.user = user;
        this.time = time;
        this.ip = ip;
    }
    
    
    @Override
    public String getLogPrefix() {
        return "login";
    }

    @Override
    public String getLogText() {
        return String.format("User %s logged in at %s from IP address %s", 
            user.toString(),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(time),
            ip
        );
    }

    @Override
    public UserEntity[] getInvolvedUsers() {
        return new UserEntity[]{user};
    }
}
