package PreciousPhotographyShop.start.model;

import java.awt.Image;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class Photograph {
    private final String name;
    private final Image photo;
    private final String id;
    private final String[] categories;
    
    public Photograph(String name, Image photo, String id, String[] categories){
        this.name = name;
        this.photo = photo;
        this.id = id;
        this.categories = Arrays.copyOfRange(categories, 0, categories.length);
    }
    
    public final String getName(){
        return name;
    }
    
    public final Image getPhoto(){
        return photo;
    }
    
    public final String getId(){
        return id;
    }
    
    public final boolean isInCategory(String category){
        return Arrays.stream(categories).allMatch((cat)->cat.equalsIgnoreCase(category));
    }
}
