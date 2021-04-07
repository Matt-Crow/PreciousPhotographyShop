package PreciousPhotographyShop.users;

import com.google.common.base.Objects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Previously split into user and user entity classes
 * 
 * @author Matt Crow, Daniel V?R?
 */

@Entity
public class UserEntity implements UserDetails {
    /*
    Which do we need?
    - id
    - username
    - name
    Having all 3 feels redundant
    */
    
    // https://stackoverflow.com/questions/40177865/hibernate-unknown-integral-data-type-for-ids
    @Id // denotes this is the primary key
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Column(name="user_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    /*@Pattern(regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",
            message = "Username should only contain alphanumeric characters, periods and underscores")*/
    @Column(name="username", nullable=false, unique=true)
    private String username;
    
    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="email", nullable=false)
    private String email;
    
    /*@Pattern(regexp = "/^[a-z ,.'-]+$/i", message = "First name is not valid")*/
    private String first_name;
    /*@Pattern(regexp = "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+", message = "Last name(s) is not valid")*/
    private String last_name;
    private String password;
    /*@Pattern(regexp = "\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.",
    message = "Address is invalid")*/
    private String address;
    private UserRole userRole;
    private Boolean locked;
    private Boolean enabled;
    
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
        this.first_name = "";
        this.last_name = "";
        this.email = "";
        this.username = "";
        this.password = "";
        this.address = "";
    }
    
    public UserEntity(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    /*
        Default Constructor
     */
    public UserEntity(String first_name, String last_name, String email, String address, String username, String password){
        this.first_name = first_name;
        this.last_name = last_name;
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


    /*
    Spring Security
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    public String getPassword() { return password; }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name() { return last_name; }
    
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
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setLast_name(String last_name) { this.first_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

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
                "First name='" + first_name + '\'' +
                ", Last name='" + last_name + '\'' +
                ", Username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
