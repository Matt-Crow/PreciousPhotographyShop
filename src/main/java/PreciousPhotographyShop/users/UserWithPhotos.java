package PreciousPhotographyShop.users;

import PreciousPhotographyShop.photographs.Photograph;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Once we meet up again, we can merge this class into User if we feel they 
 * should have this functionality.
 * 
 * @author Matt Crow
 */
public class UserWithPhotos extends User {
    private final HashSet<Photograph> ownedPhotographs;
    
    public UserWithPhotos(String name, String email) {
        super(name, email);
        ownedPhotographs = new HashSet<>();
    }
    
    public final void addPhotograph(Photograph photo){
        this.ownedPhotographs.add(photo);
    }

    public final List<Photograph> getPhotos(){
        return this.ownedPhotographs.stream().collect(Collectors.toList());
    }
}
