package PreciousPhotographyShop.users;

import com.google.common.base.Objects;
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
 * Previously split into user and user entity classes
 * 
 * @author Matt Crow, Daniel V?R?
 */

@Entity
public class UserEntity {
    /*
    Which do we need?
    - id
    - username
    - name
    Having all 3 feels redundant
    */
    
    // https://stackoverflow.com/questions/40177865/hibernate-unknown-integral-data-type-for-ids
    @Id // denotes this is the primary key
    @Column(name="id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name="username", nullable=false, unique=true)
    private String username;
    
    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="email", nullable=false)
    private String email;
    
    @ElementCollection
    @CollectionTable(
        name = "seller_to_photo",
        joinColumns = @JoinColumn(name = "id")
    )
    Set<String> photoIds = new HashSet<>();
    
    public UserEntity(){
        // requires no-arg ctro
        // I hate reflection
    }
    
    public UserEntity(String name, String email){
        this.name = name;
        this.email = email;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPhotoIds(Set<String> photoIds){
        this.photoIds = photoIds;
    }
    
    public String getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public Set<String> getPhotoIds(){
        return photoIds;
    }
    
    @Override
    public boolean equals(Object obj){
        if( this == obj ) return true;
        if(!(obj instanceof UserEntity))
            return false;
        UserEntity user = (UserEntity)obj;
        return Objects.equal(this.id, user.id)
            && Objects.equal(this.name, user.name)
            && Objects.equal(this.email, user.email)
            ;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + java.util.Objects.hashCode(this.id);
        hash = 71 * hash + java.util.Objects.hashCode(this.name);
        hash = 71 * hash + java.util.Objects.hashCode(this.email);
        return hash;
    }
    
    @Override
    public String toString(){
        return String.format(
            "User{"
            + "name='%s',"
            + "email='%s'"
            + "id='%s'"
            + "}"
            , name, email, id
        );
    }
}
