package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.databaseInterface.LocalFileSystem;
import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * The WebsiteLog is the primary log used by the website. This file must be
 * encrypted and as secure as possible.
 * 
 * @author Matt Crow
 */
@Component
public class WebsiteLog extends AbstractLog {
    
    // todo: actually encrypt it
    private String encrypt(String message){
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        return sb.toString();
    }
    
    public final String getLogFilePathForToday() throws IOException{
        // don't include time (hrs, min, etc), as we want one log per day
        String today = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalTime.now());
        String fileName = String.format("websiteLog%s.txt", today);
        return Paths.get(LocalFileSystem.getInstance().getLogFolder().getAbsolutePath(), fileName).toString();
    }
    
    @Override
    public void logEvent(AbstractLoggedEvent event) throws IOException {
        File logFile = new File(getLogFilePathForToday());
        try(
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
        ){
            writer.write(encrypt(event.getMessageToLog()));
        }
    }
}
