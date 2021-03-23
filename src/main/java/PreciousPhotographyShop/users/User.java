package PreciousPhotographyShop.users;

import org.hibernate.annotations.GenericGenerator;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 *
 * @author Matt Crow, Daniel
 */

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String email;

    // Default Constructor
    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.id = null;
    }

    // Empty Constructor
    public User() {
        this.name = "";
        this.email = "";
    }

    public final String getName(){
        return name;
    }
    
    public final String getEmail(){
        return email;
    }

    public String getId(){ return id; }

    public void setEmail(String email) { this.email = email; }

    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if( this == o) return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name)
                && Objects.equals(this.email, user.email);

    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public void setId(String id) { this.id = id; }
}
