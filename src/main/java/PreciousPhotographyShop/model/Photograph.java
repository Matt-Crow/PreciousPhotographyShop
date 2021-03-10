package PreciousPhotographyShop.model;

import java.awt.Image;

/**
 *
 * @author Matt
 */
public class Photograph {
    private final String name;
    private final Image photo;
    
    public Photograph(String name, Image photo){
        this.name = name;
        this.photo = photo;
    }
    
    public final String getName(){
        return name;
    }
    
    public final Image getPhoto(){
        return photo;
    }
}
