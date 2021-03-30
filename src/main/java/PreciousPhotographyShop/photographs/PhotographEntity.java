package PreciousPhotographyShop.photographs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
}
