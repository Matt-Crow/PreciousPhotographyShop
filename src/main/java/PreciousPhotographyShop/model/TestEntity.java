package PreciousPhotographyShop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Matt
 */
@Entity // create a database table for this
public class TestEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String message;
    
    public TestEntity(int id, String message){
        this.id = id;
        this.message = message;
    }
    
    public TestEntity(){
        
    }
    
    // Hibernate doesn't allow final
    public Integer getId(){
        return id;
    }
    
    public String getMessage(){
        return message;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
}
