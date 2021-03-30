package PreciousPhotographyShop.testsAndExamples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Matt
 */
@Entity // create a database table for this
@Table(name = "test_entity")
public class TestEntity {
    @Column(name = "test_id")
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name = "message")
    private String message;
    
    @ElementCollection // "this is a collection of primitives or Embeddables"
    @CollectionTable( // "store these in a bridge table"
        name = "test_entity_id_bridge",
        joinColumns = @JoinColumn(name = "test_id")
    )
    @Column(name="related_ids")
    private Set<String> relatedIds;
    
    public TestEntity(String id, String message){
        this.id = id;
        this.message = message;
        relatedIds = new HashSet<>();
    }
    
    public TestEntity(){
        
    }
    
    // Hibernate doesn't allow final
    public String getId(){
        return id;
    }
    
    public String getMessage(){
        return message;
    }
    
    public Set<String> getRelatedIds(){
        return this.relatedIds;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public void setRelatedIds(Set<String> relatedIds){
        this.relatedIds = relatedIds;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Test Entity#%s: \"%s\" Related to %s", 
            id,
            message,
            Arrays.toString(relatedIds.toArray())
        );
    }
}
