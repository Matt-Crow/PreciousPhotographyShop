package PreciousPhotographyShop.categories;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the database entry class for categories.
 * 
 * @author Matt Crow
 */
@Entity
public class CategoryEntity {
    @Id
    @Column(name="category_name", nullable=false, unique=true)
    private String name;
    
    @Column(name="parent_name", nullable=true, unique=false)
    private String parentName;
    
    public CategoryEntity(){}
    
    public CategoryEntity(Category asObject){
        this();
        setName(asObject.getName());
        if(asObject.hasParentCategory()){
            setParentName(asObject.getParent().getName());
        }
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setParentName(String parentName){
        this.parentName = parentName;
    }
    
    public String getName(){
        return name;
    }
    
    public String getParentName(){
        return parentName;
    }
}
