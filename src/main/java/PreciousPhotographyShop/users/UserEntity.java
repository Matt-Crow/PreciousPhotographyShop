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
 * VERY IMPORTANT: a user's primary key is their ID, as their username and email
 * can change.
 * 
 * @author Matt Crow, Daniel V
 */

@Entity
public class UserEntity {    
    // https://stackoverflow.com/questions/40177865/hibernate-unknown-integral-data-type-for-ids
    @Id // denotes this is the primary key
    @Column(name="user_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    /*@Pattern(regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",
            message = "Username should only contain alphanumeric characters, periods and underscores")*/
    @Column(name="username", nullable=false, unique=false)
    private String username;
    // Philip says he wants to allow duplicate usernames to encourage exploration
    
    @Column(name="email", nullable=false, unique=true)
    private String email;
    
    private String password;
    /*@Pattern(regexp = "\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.",
    message = "Address is invalid")*/
    private String address;
    
    @ElementCollection
    @CollectionTable(
        name = "seller_to_photo",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name="photo_id")
    Set<String> photoIds = new HashSet<>();
    
    public UserEntity(){
        // requires no-arg ctro
        // I hate reflection
        this.email = "";
        this.username = "";
        this.password = "";
        this.address = "";
    }
    
    public UserEntity(String username, String email){
        this.username = username;
        this.email = email;
    }
    
    /*
        Default Constructor
     */
    public UserEntity(String email, String address, String username, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    
    public String getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getPassword() { return password; }

    public String getAddress() { return address; }

    public Set<String> getPhotoIds(){
        return photoIds;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setAddress(String address) { this.address = address; }

    public void setPassword(String password) { this.password = password; }

    public void setPhotoIds(Set<String> photoIds){
        this.photoIds = photoIds;
    }
    
    
    
    @Override
    public boolean equals(Object obj){
        if( this == obj ) return true;
        if(!(obj instanceof UserEntity))
            return false;
        UserEntity user = (UserEntity)obj;
        return Objects.equal(this.id, user.id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + java.util.Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public String toString() {
        return "User{" +
                ", Username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
