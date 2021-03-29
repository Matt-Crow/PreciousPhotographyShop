package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.users.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
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
        
        storeUser(new User("John Doe", "johndoe@nonexistant.com"));
    }

    @Override
    public String storeUser(User user) {
        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
        }
        users.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public String storePhotograph(Photograph photo) {
        if(photo.getId() == null){
            photo.setId(UUID.randomUUID().toString());
        }
        photos.put(photo.getId(), photo);
        return photo.getId();
    }

    @Override
    public Photograph getPhotograph(String id, boolean withWatermark) {
        // todo: do stuff if withWatermark is true
        return photos.get(id);
    }

    // added by Daniel R, unsure about the method with boolean withWatermark
    // Matt: I've updated the documentation for getPhotograph(String, boolean) to
    //       make it clearer.
    public Photograph getPhotograph(String id){
         return getPhotograph(id, true);
    }

    @Override
    public Set<Photograph> getPhotographsByCategory(String category) {
        return photos.values().stream().filter((photo)->{
            return category == null || photo.isInCategory(category);
        }).collect(Collectors.toSet());
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
    public Set<String> getAllCategories() {
        return photos.values().stream().flatMap((photograph)->{
            return photograph.getCategories().stream();
        }).map((catName)->{
            return catName.toUpperCase();
        }).collect(Collectors.toSet());
    }

    @Override
    public List<String> getAllPhotoIds() {
        return photos.keySet().stream().collect(Collectors.toList());
    }
}
