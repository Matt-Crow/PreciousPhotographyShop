package PreciousPhotographyShop.users;

import PreciousPhotographyShop.photographs.Photograph;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Matt Crow, Daniel
 */


public class User {
    private final String name;
    private final String email;
    // may remove
    private final HashSet<Photograph> ownedPhotographs;
    private String id;
    
    public User(String name, String email){
        this.name = name;
        this.email = email;
        ownedPhotographs = new HashSet<>();
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
    
    public final void addPhotograph(Photograph photo){
        this.ownedPhotographs.add(photo);
    }

    public final List<Photograph> getPhotos(){
        return this.ownedPhotographs.stream().collect(Collectors.toList());
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
