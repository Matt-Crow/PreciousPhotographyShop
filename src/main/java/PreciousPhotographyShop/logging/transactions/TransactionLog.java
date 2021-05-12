package PreciousPhotographyShop.logging.transactions;

import PreciousPhotographyShop.logging.AbstractLog;

/**
 *
 * @author Matt
 */
public class TransactionLog extends AbstractLog {

    public TransactionLog(String path) {
        super(path);
    }

    @Override
    protected String format(String eventString) {
        return eventString;
    }
}
