package PreciousPhotographyShop.databaseInterface;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * This serves as an entry in the bridge table between PhotographEntitys and
 * CategoryEntitys. I'm using this in lieu of @ManyToMany to minimize the amount
 * of memory used, as @ManyToMany requires storing all CategoryEntitys a photo 
 * belongs to, each of which stores all the PhotographEntitys belonging to it,
 * each of which stores all the CategoryEntitys they belong to...
 * 
 * @author Matt
 */
@Entity
public class PhotographToCategoryTableEntry {
    
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name="Photograph_ID")
    private String photographId;
    
    @Column(name="Category_ID")
    private String categoryId;
    
    public PhotographToCategoryTableEntry(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setPhotographId(String photographId){
        this.photographId = photographId;
    }
    
    public void setCategoryId(String categoryId){
        this.categoryId = categoryId;
    }
    
    public String getId(){
        return id;
    }
    
    public String getPhotographId(){
        return photographId;
    }
    
    public String getCategoryId(){
        return categoryId;
    }
}
