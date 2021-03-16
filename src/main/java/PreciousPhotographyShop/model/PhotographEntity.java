package PreciousPhotographyShop.model;

import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * File path is just
 *     fileFolder/ID
 * @author Matt
 */

@Entity
public class PhotographEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @ManyToMany
    private Collection<CategoryEntity> categories;
    
    public PhotographEntity(){
        categories = new LinkedList<>();
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setCategories(Collection<CategoryEntity> categories){
        this.categories = categories;
    }
    
    public String getId(){
        return id;
    }
    
    public Collection<CategoryEntity> getCategories(){
        return categories;
    }
}
