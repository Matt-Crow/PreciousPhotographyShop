package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import PreciousPhotographyShop.logging.events.PurchaseEvent;
import PreciousPhotographyShop.logging.logs.TransactionLog;
import PreciousPhotographyShop.logging.logs.UserLog;
import PreciousPhotographyShop.logging.logs.WebsiteLog;
import PreciousPhotographyShop.users.UserEntity;
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
    @Autowired private TransactionLog transactionLog;
    
    public final void logEvent(AbstractLoggedEvent event){
        try { // all events go to the website log
            websiteLog.logEvent(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(event instanceof PurchaseEvent){
            try {
                transactionLog.logEvent(event);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        for(UserEntity e : event.getInvolvedUsers()){
            try {
                new UserLog(e).logEvent(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
