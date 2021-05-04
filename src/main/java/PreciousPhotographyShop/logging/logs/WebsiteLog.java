package PreciousPhotographyShop.logging.logs;

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
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        return sb.toString();
    }
}
