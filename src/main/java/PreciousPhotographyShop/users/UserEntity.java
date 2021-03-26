package PreciousPhotographyShop.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Matt Crow
 */

@Entity
public class UserEntity {
    // https://stackoverflow.com/questions/40177865/hibernate-unknown-integral-data-type-for-ids
    @Id // denotes this is the primary key
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    private String name;
    private String email;
    
    public UserEntity(){
        // requires no-arg ctro
        // I hate reflection
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    
}
