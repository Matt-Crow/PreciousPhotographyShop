package PreciousPhotographyShop.categories;

import PreciousPhotographyShop.databaseInterface.PhotographToCategoryTableEntry;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * not sure if needs ID, as name is a good primary key
 * @author Matt
 */
@Entity
public class CategoryEntity {
    @Id
    @Column(name="categoryName")
    private String name;
    
    @OneToMany()
    private Collection<PhotographToCategoryTableEntry> photoIdMappings;
    
    public CategoryEntity(){
        photoIdMappings = new LinkedList<>();
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPhotoIdMappings(Collection<PhotographToCategoryTableEntry> photoIdMappings){
        this.photoIdMappings = photoIdMappings;
    }
    
    public String getName(){
        return name;
    }
    
    public Collection<PhotographToCategoryTableEntry> getPhotoIdMappings(){
        return photoIdMappings;
    }
}
