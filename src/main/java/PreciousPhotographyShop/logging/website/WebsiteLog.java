package PreciousPhotographyShop.logging.website;

import PreciousPhotographyShop.logging.AbstractLog;
import PreciousPhotographyShop.logging.encryption.Encrypter;
import PreciousPhotographyShop.logging.encryption.EncryptionProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The WebsiteLog is the primary log used by the website. This file must be
 * encrypted and as secure as possible.
 * 
 * @author Matt Crow
 */
public class WebsiteLog extends AbstractLog {

    private final Encrypter encrypter;
    
    public WebsiteLog(String path){
        super(path);
        
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
    
    public final String decryptContentsUsing(Encrypter decrypter) throws IOException{
        String contents = Arrays.stream(getText().split("\n")).map((line)->{
            String dec = null;
            try {
                // need to decrypt line by line, as the \n is NOT encrypted in the log file
                dec = decrypter.decrypt(line);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return dec;
        }).filter((dec)->dec != null).collect(Collectors.joining("\n"));
        return contents;
    }
    
    public static void main(String[] args) throws Exception{
        WebsiteLog log = new WebsiteLogFolder().getOrCreateLogForToday();
           
        System.out.println(log.decryptContentsUsing(log.encrypter));
    }
}
