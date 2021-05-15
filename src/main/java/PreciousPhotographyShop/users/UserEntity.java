package PreciousPhotographyShop.users;

import com.google.common.base.Objects;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Previously split into user and user entity classes
 * 
 * VERY IMPORTANT: a user's primary key is their ID, as their username and email
 * can change.
 * 
 * @author Matt Crow, Daniel V
 */

@Entity
@Table(name="user")
public class UserEntity implements UserDetails {

    @Id
    @Column(name="user_id")
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    
    @Column(name="username", nullable=false, unique=false)
    private String username;

    @Column(name="email", nullable=false, unique=true)
    private String email;
    
    @Column(name="profile_picture_id", nullable=true, unique=false)
    private String profilePictureId;
    
    @Column(name="description", nullable=true, unique=false)
    private String description = "No description";

    @Column(name="password", nullable=false, unique=false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;

    private Boolean locked = false;

    private Boolean enabled = true;
    
    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @CollectionTable(name = "seller_to_photo", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name="photo_id")
    Set<String> photoIds;

    public UserEntity(){
        this.email = "";
        this.username = "";
        this.password = "";
        this.profilePictureId = null;
        photoIds = new HashSet<>();
    }

    public UserEntity(String username, String email){
        this();
        this.username = username;
        this.email = email;
    }
    
    /*
        Default Constructor
     */
    public UserEntity(String email, String username, String password){
        this(username, email);
        this.password = password;
    }
    
    /*
        Getters
     */

    public String getId(){ 
        return id; 
    }
    
    @Override
    public String getUsername(){ 
        return username; 
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getProfilePictureId(){
        return profilePictureId;
    }
    
    public String getDescription(){
        return description;
    }
    
    @Override
    public String getPassword() { 
        return password; 
    }
    
    public Set<String> getPhotoIds(){ 
        return photoIds; 
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
        return true; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }
    
    
    
    /*
        Setters
     */

    public void setId(String id){ 
        this.id = id; 
    }
    
    public void setUsername(String username){ 
        this.username = username; 
    }
    
    public void setEmail(String email){ 
        this.email = email; 
    }
    
    public void setProfilePictureId(String profilePictureId){
        this.profilePictureId = profilePictureId;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
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
        return String.format(
            "User{" +
                "Username='%s'," +
                "description='%s'" +
                "id='%s'"+
            "}",
            username,
            description,
            id
        );
    }
}
