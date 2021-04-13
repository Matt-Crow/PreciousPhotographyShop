package PreciousPhotographyShop.photographs;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * File path is just
 *     fileFolder/ID
 * @author Matt
 */

@Entity
@SecondaryTable(name = "seller_to_photo") // need this for bridge table
public class PhotographEntity {
    @Id
    @Column(name="photo_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="description", nullable=true)
    private String description = "no description";
    
    @Column(name="price", nullable=false)
    private double price;
    
    @Column(name="isRecurring", nullable=false)
    private boolean isRecurring;
    
    /*
    I'm using this in lieu of @ManyToMany to minimize the amount
    of memory used, as @ManyToMany requires storing all CategoryEntitys a photo 
    belongs to, each of which stores all the PhotographEntitys belonging to it,
    each of which stores all the CategoryEntitys they belong to...
    
    Creates a bridge table between PhotographEntity and CategoryEntity
    photo_id is a foreign key to this class,
    category_name is a foreign key to the category
    
    Setup will look similar in CategoryEntity
    */
    @ElementCollection
    @CollectionTable(
        name = "photo_to_category",
        joinColumns = @JoinColumn(name = "photo_id")
    )
    @Column(name = "category_name")
    Set<String> categoryNames = new HashSet<>();
    
    /*
    Is there some way of inserting this into the seller_to_photo table
    with a foreign key for this?
    
    This might work.
    
    I think it will do the following:
        Upon saving this entity, inserts the following record into the
        seller_to_photo table:
            photo_id | user_id
            this.id  | this.ownerId
        The foreign key is automatically set by @SecondaryTable above
    */
    @Column(
        name = "user_id", //
        table = "seller_to_photo" // only stored in bridge table
    )
    String ownerId;
    
    @Column(name="total_rating")
    long totalRating = 0;
    
    @Column(name="num_ratings")
    long numRatings = 0;
    
    @Transient // "Don't store this in the database"
    BufferedImage photo;
    
    public PhotographEntity(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setIsRecurring(boolean isRecurring){
        this.isRecurring = isRecurring;
    }
    
    public void setCategoryNames(Set<String> categoryNames){
        this.categoryNames = categoryNames;
    }
    
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }
    
    public void setTotalRating(long totalRating){
        this.totalRating = totalRating;
    }
    
    public void setNumRatings(long numRatings){
        this.numRatings = numRatings;
    }
    
    public void setPhoto(BufferedImage photo){
        this.photo = photo;
    }
    
    public String getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public double getPrice(){
        return price;
    }
    
    public boolean getIsRecurring(){
        return isRecurring;
    }
    
    public Set<String> getCategoryNames(){
        return categoryNames;
    }
    
    public String getOwnerId(){
        return ownerId;
    }
    
    public long getTotalRating(){
        return totalRating;
    }
    
    public long getNumRatings(){
        return numRatings;
    }
    
    public BufferedImage getPhoto(){
        return photo;
    }
    
    public final boolean isInCategory(String catName){
        // need to figure out where we want to put this
        return this.categoryNames.stream().anyMatch((cat)->cat.equalsIgnoreCase(catName));
    }
    
    public final double getAverageRating(){
        return ((double)totalRating) / numRatings;
    }
}
