package PreciousPhotographyShop.testsAndExamples;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Matt
 */
@Entity
public class EntityB {
    @Column(name = "b_id")
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    
    @ElementCollection
    @CollectionTable(
        name = "a_to_b",
        joinColumns = @JoinColumn(name = "b_id")
    )
    @Column(name = "a_id")
    Set<String> aIds = new HashSet<>();
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public void setAIds(Set<String> aIds){
        this.aIds = aIds;
    }
    
    public Set<String> getAIds(){
        return aIds;
    }
    
    @Override
    public String toString(){
        return String.format("B#%s (%s)", id, aIds.toString());
    }
}