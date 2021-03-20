package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.users.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
//@Repository("userDB")
//@Service
public class BadExampleDatabase implements DatabaseInterface {
    private final HashMap<String, User> users;
    private final HashMap<String, Photograph> photos;
    
    public BadExampleDatabase(){
        users = new HashMap<>();
        photos = new HashMap<>();
        
        storeUser(new User("John Doe", "johndoe@nonexistant.com", UUID.randomUUID().toString() ));
    }

    @Override
    public void storeUser(User user) {
        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
        }
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void storePhotograph(Photograph photo) {
        if(photo.getId() == null){
            photo.setId(UUID.randomUUID().toString());
        }
        photos.put(photo.getId(), photo);
    }

    @Override
    public Photograph getPhotograph(String id, boolean withWatermark) {
        // todo: do stuff if withWatermark is true
        return photos.get(id);
    }

    // added by Daniel R, unsure about the method with boolean withWatermark
    public Photograph getPhotograph(String id){
         return getPhotograph(id, true);
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        return photos.values().stream().filter((photo)->{
            return Arrays.stream(categories).anyMatch(photo::isInCategory);
        }).toArray((size)->new Photograph[size]);
    }

    @Override
    public HashMap<String, Photograph> getAllPhotos() {
        return photos;
    }

    @Override
    public int deletePhotoByID(String id) {
        Photograph temp = getPhotograph(id);
        if(temp == null)
            return 0;
        else
            photos.remove(id);
        return 1;
    }

    @Override
    public int updatePhotoByID(String id, Photograph photograph){
        return 0; // Implement later
    }
    
    @Override
    public List<String> getAllCategories() {
        return photos.values().stream().flatMap((photograph)->{
            return photograph.getCategories().stream();
        }).map((catName)->{
            return catName.toUpperCase();
        }).distinct().collect(Collectors.toList());
    }
}
