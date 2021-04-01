package PreciousPhotographyShop.users;

import org.hibernate.annotations.GenericGenerator;

import javax.annotation.MatchesPattern;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "/^[a-z ,.'-]+$/i", message = "First name is not valid")
    private String first_name;
    @Pattern(regexp = "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+", message = "Last name(s) is not valid")
    private String last_name;
    @Pattern(regexp = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",
            message = "Username should only contain alphanumeric characters, periods and underscores")
    private String username;
    private String password;
    @Pattern(regexp = "\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.",
    message = "Address is invalid")
    private String address;
    private String email;

    /*
        Default Constructor
     */
    public User(String first_name, String last_name, String email, String address, String username, String password){
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    /*
        Empty Constructor
     */
    public User() {
        this.first_name = "";
        this.last_name = "";
        this.email = "";
        this.username = "";
        this.password = "";
        this.address = "";
    }

    /*
        Getter methods
     */
    public String getId(){ return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public final String getFirst_name(){
        return first_name;
    }

    public final String getLast_name() { return last_name; }

    public final String getEmail(){
        return email;
    }

    public String getAddress() { return address; }

    /*
        Setter methods
     */

    public void setId(String id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setLast_name(String last_name) { this.first_name = last_name; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public void setUsername(String username) { this.username = username; }

    public void setAddress(String address) { this.address = address; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public boolean equals(Object o) {
        if( this == o) return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.first_name, user.first_name)
                && Objects.equals(this.email, user.email);

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
