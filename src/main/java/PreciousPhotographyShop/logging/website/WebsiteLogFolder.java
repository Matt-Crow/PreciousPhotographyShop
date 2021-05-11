package PreciousPhotographyShop.logging.website;

import PreciousPhotographyShop.logging.AbstractLogFolder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
public class WebsiteLogFolder extends AbstractLogFolder<WebsiteLog> {
    
    public final String[] getAllWebsiteLogNames() throws IOException{
        File root = this.getFolder();
        return Arrays.stream(root.listFiles()).map((File file)->{
            return file.getName();
        }).toArray(String[]::new);
    }
    
    public final WebsiteLog getOrCreateLogForToday() throws IOException{
        File todaysFile = getOrCreateFile(getFileNameForToday());
        return new WebsiteLog(todaysFile.getAbsolutePath());
    }
    
    private String getFileNameForToday(){
        // don't include time (hrs, min, etc), as we want one log per day
        String today = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now());
        return String.format("websiteLog%s.txt", today);
    }
    
    @Override
    protected String getLogSubfolderName() {
        return "website";
    }

    @Override
    public WebsiteLog getLogByName(String name) throws IOException {
        String path = Paths.get(this.getFolder().getAbsolutePath(), name).toString();
        return new WebsiteLog(path);
    }
}
