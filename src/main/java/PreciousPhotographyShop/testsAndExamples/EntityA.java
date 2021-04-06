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
public class EntityA {
    @Column(name = "a_id")
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;
    
    @ElementCollection
    @CollectionTable(
        name = "a_to_b",
        joinColumns = @JoinColumn(name = "a_id")
    )
    @Column(name = "b_id")
    Set<String> bIds = new HashSet<>();
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public void setBIds(Set<String> bIds){
        this.bIds = bIds;
    }
    
    public Set<String> getBIds(){
        return bIds;
    }
    
    @Override
    public String toString(){
        return String.format("A#%s (%s)", id, bIds.toString());
    }
}
