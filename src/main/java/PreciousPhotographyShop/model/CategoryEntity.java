package PreciousPhotographyShop.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 * not sure if needs ID, as name is a good primary key
 * @author Matt
 */
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String name;
    
    @ManyToMany
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
