package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.PhotographEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Matt
 */
@Entity
public class PhotographToCategoryTableEntry {
    
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @ManyToOne()
    private PhotographEntity photograph;
    
    @ManyToOne()
    private CategoryEntity category;
    
    public PhotographToCategoryTableEntry(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setPhotograph(PhotographEntity photograph){
        this.photograph = photograph;
    }
    
    public void setCategory(CategoryEntity category){
        this.category = category;
    }
    
    public String getId(){
        return id;
    }
    
    public PhotographEntity getPhotograph(){
        return photograph;
    }
    
    public CategoryEntity getCategory(){
        return category;
    }
}
