package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import PreciousPhotographyShop.logging.logs.WebsiteLog;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The LogService is used for storing and retrieving events from a variety of
 * logs used by the website.
 * 
 * @author Matt Crow
 */
@Service // you can use '@Autowired LogService myLogServ' in a class' fields to use this 
public class LogService {
    @Autowired private WebsiteLog websiteLog;
    
    public final void logEvent(AbstractLoggedEvent event){
        try {
            websiteLog.logEvent(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
