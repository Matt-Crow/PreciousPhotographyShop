package PreciousPhotographyShop.model;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.UUID;
import java.util.Collection;

/**
 *
 * @author Matt
 */
public class Photograph {
    private String name;
    private BufferedImage photo;
    //private UUID id;
    private String id;
    private String[] categories;
    
    //private final String name;
    
    //private final String[] categories;
    
    public Photograph(String name, BufferedImage photo, String id, String[] categories){
        this.name = name;
        this.photo = photo;
        this.id = id;
        this.categories = Arrays.copyOfRange(categories, 0, categories.length);
    }

    public void setName(String name){this.name = name;}
    public void setPhoto(BufferedImage photo){this.photo = photo;}
    //public void setId(UUID id){this.id = id;}
    public void setCategories(String[] categories){this.categories = categories;}

    public String getName(){
        return name;
    }
    
    public final BufferedImage getPhoto(){
        return photo;
    }
    
    /*
    public UUID getId(){
        return id;
    }*/
    
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
