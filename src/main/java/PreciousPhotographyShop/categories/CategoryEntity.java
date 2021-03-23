package PreciousPhotographyShop.categories;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * not sure if needs ID, as name is a good primary key
 * @author Matt
 */
@Entity
public class CategoryEntity {
    @Id
    @Column(name="categoryName")
    private String name;
        
    public CategoryEntity(){
        
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
}
