package PreciousPhotographyShop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    // probably do a categories table? 
    // many-to-many relationship between categories and photos
    
    public PhotographEntity(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setPhoto(byte[] photo){
        this.photo = photo;
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
    
    /*
    public String getLocalFilePath(){
        return localFilePath;
    }
    */
}
