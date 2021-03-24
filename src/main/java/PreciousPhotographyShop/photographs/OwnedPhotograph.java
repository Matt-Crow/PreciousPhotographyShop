package PreciousPhotographyShop.photographs;

import PreciousPhotographyShop.users.User;
import java.awt.image.BufferedImage;

/**
 * We can discuss later exactly what properties Photographs need,
 * so we may wind up merging this into Photograph
 * 
 * @author Matt Crow
 */
public class OwnedPhotograph extends Photograph {
    private final User owner;
    
    public OwnedPhotograph(String name, BufferedImage photo, String[] categories, User owner) {
        super(name, photo, categories);
        this.owner = owner;
    }
    
    public final User getOwner(){
        return owner;
    }
}
