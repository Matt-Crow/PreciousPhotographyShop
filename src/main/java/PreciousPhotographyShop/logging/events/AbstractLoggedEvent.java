package PreciousPhotographyShop.logging.events;

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
     * @return the un-encrypted text to insert into relevant log files.
     */
    public String getLoggableString();
}
