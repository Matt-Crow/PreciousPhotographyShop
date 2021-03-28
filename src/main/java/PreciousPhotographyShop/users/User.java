package PreciousPhotographyShop.users;

import java.util.Objects;

/**
 *
 * @author Matt Crow, Daniel
 */


public class User {
    private final String name;
    private final String email;
    private String id;
    
    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.id = null;
    }
    
    public final String getName(){
        return name;
    }
    
    public final String getEmail(){
        return email;
    }
    
    // needed for after returning from DB
    public final void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
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
