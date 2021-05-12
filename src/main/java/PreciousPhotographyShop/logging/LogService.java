package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import PreciousPhotographyShop.logging.events.PurchaseEvent;
import PreciousPhotographyShop.logging.transactions.TransactionLogFolder;
import PreciousPhotographyShop.logging.users.UserLog;
import PreciousPhotographyShop.logging.users.UserLogFolder;
import PreciousPhotographyShop.logging.website.WebsiteLogFolder;
import PreciousPhotographyShop.users.UserEntity;
import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 * The LogService is used for storing and retrieving events from a variety of
 * logs used by the website.
 * 
 * @author Matt Crow
 */
@Service // you can use '@Autowired LogService myLogServ' in a class' fields to use this 
public class LogService {
    private final WebsiteLogFolder websiteLogFolder;
    private final TransactionLogFolder transactionLogFolder;
    private final UserLogFolder userLogFolder;
    
    public LogService(WebsiteLogFolder websiteLogFolder, TransactionLogFolder transactionLogFolder, UserLogFolder userLogFolder){
        this.websiteLogFolder = websiteLogFolder;
        this.transactionLogFolder = transactionLogFolder;
        this.userLogFolder = userLogFolder;
    }
    
    public final void logEvent(AbstractLoggedEvent event){
        try { // all events go to the website log
            websiteLogFolder.getOrCreateLogForToday().logEvent(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(event instanceof PurchaseEvent){
            try {
                transactionLogFolder.getOrCreateLogForToday().logEvent(event);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
        
        for(UserEntity e : event.getInvolvedUsers()){
            try {
                userLogFolder.getOrCreateLogForUser(e).logEvent(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public final UserLog getLogForUser(UserEntity user) throws IOException{
        return this.userLogFolder.getOrCreateLogForUser(user);
    }
}
