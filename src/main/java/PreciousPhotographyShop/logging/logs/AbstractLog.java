package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import java.io.IOException;

/**
 *
 * @author Matt
 */
public abstract class AbstractLog {
    public abstract void logEvent(AbstractLoggedEvent event) throws IOException;
}
