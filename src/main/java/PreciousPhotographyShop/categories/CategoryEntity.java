package PreciousPhotographyShop.categories;

import PreciousPhotographyShop.photographs.PhotographEntity;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

/**
 * not sure if needs ID, as name is a good primary key
 * @author Matt
 */
@Entity
public class CategoryEntity {
    @Id
    @Column(name="categoryName")
    private String name;
    
    @ManyToMany
    @JoinColumn(name="photoId")
    private Collection<PhotographEntity> photos;
    
    public CategoryEntity(){
        photos = new LinkedList<>();
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPhotos(Collection<PhotographEntity> photos){
        this.photos = photos;
    }
    
    public String getName(){
        return name;
    }
    
    public Collection<PhotographEntity> getPhotos(){
        return photos;
    }
}
