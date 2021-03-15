package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Matt
 */
@Repository("userDB")
public class BadExampleDatabase implements DatabaseInterface {
    private final HashMap<UUID, User> users;
    private final HashMap<UUID, Photograph> photos;
    
    public BadExampleDatabase(){
        users = new HashMap<>();
        photos = new HashMap<>();
        
        storeUser(new User("John Doe", "johndoe@nonexistant.com", UUID.randomUUID() ));
    }

    @Override
    public void storeUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void storePhotograph(Photograph photo) {
        photos.put(photo.getId(), photo);
    }

    @Override
    public Photograph getPhotograph(UUID id, boolean withWatermark) {
        // todo: do stuff if withWatermark is true
        return photos.get(id);
    }

    // added by Daniel R, unsure about the method with boolean withWatermark
    @Override
    public Photograph getPhotograph(UUID id){
         return photos.get(id);
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        return photos.values().stream().filter((photo)->{
            return Arrays.stream(categories).anyMatch(photo::isInCategory);
        }).toArray((size)->new Photograph[size]);
    }

    @Override
    public HashMap<UUID, Photograph> getAllPhotos() {
        return photos;
    }

    @Override
    public int deletePhotoByID(UUID id) {
        Photograph temp = getPhotograph(id);
        if(temp == null)
            return 0;
        else
            photos.remove(id);
        return 1;
    }

    @Override
    public int updatePhotoByID(UUID id, Photograph photograph){
        return 0; // Implement later

    }
}
