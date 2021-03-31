package PreciousPhotographyShop.photographs;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

/**
 * File path is just
 *     fileFolder/ID
 * @author Matt
 */

@Entity
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
}
