package PreciousPhotographyShop.photographs;

/**
 * What do we need to put here?
 * Rights, seller, buyer, etc?
 * 
 * @author Matt Crow
 */
public class PurchaseDetails {
    private final PhotographEntity photo;
    
    public PurchaseDetails(PhotographEntity photo){
        this.photo = photo;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Purchase Details:\n\t* Photo: %s",
            photo.toString()
        );
    }
}
