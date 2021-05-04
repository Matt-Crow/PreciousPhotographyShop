package PreciousPhotographyShop.logging.logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
public class TransactionLog extends AbstractLog {
    

    @Override
    protected String getLogSubfolderName() {
        return "transactions";
    }

    @Override
    protected String getLogFileName() {
        // date, but not time. This means on transaction log per day
        return String.format("transactions%s.txt", DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now()));
    }

    @Override
    protected String format(String eventString) {
        return eventString;
    }
}
