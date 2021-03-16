package PreciousPhotographyShop.model;

import java.util.Objects;

/**
 *
 * @author Matt
 */

public class User {
    private  String name;
    private  String email;
    private  String id;
    
    public User(String name, String email, String id){
        this.name = name;
        this.email = email;
        this.id = id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getId(){
        return id;
    }

    public void setID(String id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public boolean equals(Object o) {
        if( this == o) return true;
        if(!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.name, user.name)
                && Objects.equals(this.email, user.email);

    }



}
