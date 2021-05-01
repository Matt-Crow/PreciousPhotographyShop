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

/**
 *
 * @author Matt
 */
public class TransactionLog extends AbstractLog {
    

    @Override
    protected String getLogSubfolderName() {
        return "transactions";
    }

    @Override
    protected String getLogFileName() {
        // date, but not time. This means on transaction log per day
        return String.format("transactions%s.txt", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalTime.now()));
    }

    @Override
    protected String format(String eventString) {
        return eventString;
    }
}
