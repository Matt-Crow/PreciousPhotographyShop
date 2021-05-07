package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.logging.encryption.Encrypter;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 * The WebsiteLog is the primary log used by the website. This file must be
 * encrypted and as secure as possible.
 * 
 * @author Matt Crow
 */
@Component
public class WebsiteLog extends AbstractLog {

    private final Encrypter encrypter;
    
    public WebsiteLog(){
        super();
        
        Encrypter temp = null;
        try {
            temp = EncryptionProvider.createDefaulEncrypter();
        } catch (Exception ex) {
            System.err.println("Failed to load encrypter. Switching to no encryption");
            ex.printStackTrace();
        }
        this.encrypter = temp;
    }
    
    @Override
    protected String getLogSubfolderName() {
        return "website";
    }

    @Override
    protected String getLogFileName() {
        // don't include time (hrs, min, etc), as we want one log per day
        String today = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now());
        return String.format("websiteLog%s.txt", today);
    }

    @Override
    protected String format(String eventString) {
        return encrypt(eventString);
    }
    
    // todo: actually encrypt it
    private String encrypt(String message){
        if(encrypter != null){
            try {
                message = encrypter.encrypt(message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return message;
    }
}
