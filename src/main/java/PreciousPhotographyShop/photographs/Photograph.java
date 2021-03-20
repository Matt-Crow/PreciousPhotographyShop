package PreciousPhotographyShop.photographs;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Matt
 */
public class Photograph {
    private final String name;
    private final BufferedImage photo;
    private String id;
    private String[] categories;
    
    
    //private final String[] categories;
    
    public Photograph(String name, BufferedImage photo, String id, String[] categories){
        this.name = name;
        this.photo = photo;
        this.id = id;
        this.categories = Arrays.copyOfRange(categories, 0, categories.length);
    }

    public void setCategories(String[] categories){
        this.categories = categories;
    }

    public String getName(){
        return name;
    }
    
    public final BufferedImage getPhoto(){
        return photo;
    }
    
    public final void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public final Collection<String> getCategories(){
        return Arrays.asList(categories);
    }
    
    public final boolean isInCategory(String category){
        return Arrays.stream(categories).allMatch((cat)->cat.equalsIgnoreCase(category));
    }


}
