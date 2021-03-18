package PreciousPhotographyShop.model;

import java.util.Objects;

/**
 *
 * @author Matt Crow, Daniel
 */
public class User {
    private String name;
    private String email;
    private String id;
    
    public User(String name, String email, String id){
        this.name = name;
        this.email = email;
        this.id = id;
    }
    
    public final String getName(){
        return name;
    }
    
    public final String getEmail(){
        return email;
    }
    
    public final String getId(){
        return id;
    }
    
    // needed for after returning from DB
    public final void setId(String id){
        this.id = id;
    }
    
    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

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
}
