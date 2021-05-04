package PreciousPhotographyShop.logging.events;

import PreciousPhotographyShop.users.UserEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is the base class for the events this package will log.
 * 
 * @author Matt Crow
 */
public interface AbstractLoggedEvent {
    
    /**
     * 
     * @return a prefix to apply to all events of this type in logs, making it
     * easier to search for and file.
     */
    public String getLogPrefix();
    
    /**
     * 
     * @return the un-encrypted text to use when inserting into relevant log 
     * files. 
     */
    public String getLogText();
    
    /**
     * 
     * @return the users involved in this event
     */
    public UserEntity[] getInvolvedUsers();
    
    /**
     * Don't override this method
     * 
     * @return the exact message to insert into the log file
     */
    public default String getMessageToLog(){
        String tag = getLogPrefix();
        return String.format(
            "<%s>%s : %s</%s>", 
            tag,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()),
            getLogText(),
            tag
        );
    }
}
