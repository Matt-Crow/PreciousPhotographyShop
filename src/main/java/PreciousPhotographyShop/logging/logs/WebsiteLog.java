package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.logging.encryption.Encrypter;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            temp = EncryptionProvider.createDefaultEncrypter();
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
    
    public static void main(String[] args) throws Exception{
        WebsiteLog log = new WebsiteLog();
        Encrypter enc = EncryptionProvider.createDefaultEncrypter();
        File f = log.getFileForToday();
        BufferedReader read = new BufferedReader(new FileReader(f));
        read.lines().map((encLine)->{
            String line = "";
            try {
                line = enc.decrypt(encLine);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return line;
        }).forEach(System.out::println);
    }
}
