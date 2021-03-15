package PreciousPhotographyShop.model;

import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Matt
 */

@Entity
public class PhotographEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
    
    private byte[] photo;
    // or we could do
    //private String localFilePath;
    
    @ManyToMany
    private Collection<CategoryEntity> categories;
    
    public PhotographEntity(){
        categories = new LinkedList<>();
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setPhoto(byte[] photo){
        this.photo = photo;
    }
    
    public void setCategories(Collection<CategoryEntity> categories){
        this.categories = categories;
    }
    
    /*
    public void setLocalFilePath(String localFilePath){
        this.localFilePath = localFilePath;
    }
    */
    
    public String getId(){
        return id;
    }
    
    public byte[] getPhoto(){
        return photo;
    }
    
    public Collection<CategoryEntity> getCategories(){
        return categories;
    }
    
    /*
    public String getLocalFilePath(){
        return localFilePath;
    }
    */
}
