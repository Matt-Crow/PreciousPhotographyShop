package PreciousPhotographyShop.logging.transactions;

import PreciousPhotographyShop.logging.AbstractLogFolder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
public class TransactionLogFolder extends AbstractLogFolder<TransactionLog>{
    
    public final TransactionLog getOrCreateLogForToday() throws IOException{
        File todaysFile = getOrCreateFile(getTodaysFileName());
        return new TransactionLog(todaysFile.getAbsolutePath());
    }
    
    private String getTodaysFileName() {
        // date, but not time. This means on transaction log per day
        return String.format("transactions%s.txt", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now()));
    }
    
    @Override
    protected String getLogSubfolderName() {
        return "transactions";
    }

    @Override
    public TransactionLog getLogByName(String name) throws IOException {
        String path = Paths.get(this.getFolder().getAbsolutePath(), name).toString();
        return new TransactionLog(path);
    }

}
