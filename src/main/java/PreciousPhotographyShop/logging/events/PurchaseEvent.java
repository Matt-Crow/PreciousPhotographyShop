package PreciousPhotographyShop.logging.events;

import PreciousPhotographyShop.photographs.PurchaseDetails;
import PreciousPhotographyShop.users.UserEntity;

/**
 *
 * @author Matt
 */
public class PurchaseEvent implements AbstractLoggedEvent {
    private final UserEntity seller;
    private final UserEntity buyer;
    private final PurchaseDetails details;
    
    public PurchaseEvent(UserEntity seller, UserEntity buyer, PurchaseDetails details){
        this.seller = seller;
        this.buyer = buyer;
        this.details = details;
    }
    
    
    @Override
    public String getLogPrefix() {
        return "purchase";
    }

    @Override
    public String getLogText() {
        return String.format(
            "%s purchased %s from %s",
            buyer.toString(),
            details.toString(),
            seller.toString()
        );
    }

}
