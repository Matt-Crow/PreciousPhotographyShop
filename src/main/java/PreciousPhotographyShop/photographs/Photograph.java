package PreciousPhotographyShop.photographs;

import PreciousPhotographyShop.users.User;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hmm. Yeah, it's looking like Daniel V might have the right idea in compressing
 * our data and entity classes together.
 * 
 * @author Matt Crow
 */
public class Photograph {
    private final User owner;
    private final String name;
    private final BufferedImage photo;
    private final String description;
    private final double basePrice;
    // private final Set<PurchaserRight> rights;
    private final Set<String> categories;
    private final boolean isRecurringSale;
    
    // set by database interface
    private String id;
    
    public Photograph(User owner, String name, BufferedImage photo, String description, double basePrice, Set<String> categories, boolean isRecurringSale){
        this.owner = owner;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.basePrice = basePrice;
        this.categories = categories.stream().collect(Collectors.toSet());
        this.isRecurringSale = isRecurringSale;
        
        this.id = null;
    }

    public void setCategories(Collection<String> categories){
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public String getName(){
        return name;
    }
    
    public final User getOwner(){
        return owner;
    }
    
    public final BufferedImage getPhoto(){
        return photo;
    }
    
    public final String getDescription(){
        return description;
    }
    
    public final double getBasePrice(){
        return basePrice;
    }
    
    public final boolean getIsRecurringSale(){
        return isRecurringSale;
    }
    
    public final void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public final Collection<String> getCategories(){
        return categories.stream().collect(Collectors.toList());
    }
    
    public final boolean isInCategory(String category){
        return categories.stream().anyMatch((cat)->cat.equalsIgnoreCase(category));
    }
}
