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
    @Column(name="photoId")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    private String name;
    
    public PhotographEntity(){
        
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
}
