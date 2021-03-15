package PreciousPhotographyShop.model;

import java.awt.Image;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author Matt
 */
public class Photograph {
    private String name;
    private Image photo;
    private UUID id;
    private String[] categories;
    
    public Photograph(String name, Image photo, UUID id, String[] categories){
        this.name = name;
        this.photo = photo;
        this.id = id;
        this.categories = Arrays.copyOfRange(categories, 0, categories.length);
    }

    public void setName(String name){this.name = name;}
    public void setPhoto(Image photo){this.photo = photo;}
    public void setId(UUID id){this.id = id;}
    public void setCategories(String[] categories){this.categories = categories;}

    public String getName(){
        return name;
    }
    
    public Image getPhoto(){
        return photo;
    }
    
    public UUID getId(){
        return id;
    }
    
    public final boolean isInCategory(String category){
        return Arrays.stream(categories).allMatch((cat)->cat.equalsIgnoreCase(category));
    }


}
