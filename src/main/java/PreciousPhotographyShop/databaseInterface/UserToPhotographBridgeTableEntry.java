package PreciousPhotographyShop.databaseInterface;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Matt
 */

@Entity
public class UserToPhotographBridgeTableEntry {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name="User_ID", nullable=false, unique=false)
    private String userId;
    
    @Column(name="Photograph_ID", nullable=false, unique=true)
    private String photographId;
    
    public UserToPhotographBridgeTableEntry(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setUserId(String userId){
        this.userId = userId;
    }
    
    public void setPhotographId(String photographId){
        this.photographId = photographId;
    }
    
    public String getId(){
        return id;
    }
    
    public String getUserId(){
        return userId;
    }
    
    public String getPhotographId(){
        return photographId;
    }
}
