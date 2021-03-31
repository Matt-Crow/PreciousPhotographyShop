package PreciousPhotographyShop.categories;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

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
    
    /*
    Creates a bridge table between PhotographEntity and CategoryEntity
    photo_id is a foreign key to the PhotographEntity,
    category_name is a foreign key to this class
    
    Setup will look similar in PhotographEntity
    */
    @ElementCollection
    @CollectionTable(
        name = "photo_to_category",
        joinColumns = @JoinColumn(name = "category_name")
    )
    @Column(name = "photo_id")
    Set<String> photoIds = new HashSet<>();
    
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
    
    public void setPhotoIds(Set<String> photoIds){
        this.photoIds = photoIds;
    }
    
    public String getName(){
        return name;
    }
    
    public String getParentName(){
        return parentName;
    }
    
    public Set<String> getPhotoIds(){
        return photoIds;
    }
}
