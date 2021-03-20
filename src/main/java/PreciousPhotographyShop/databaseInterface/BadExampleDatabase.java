package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.users.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
//@Repository("userDB")
public class BadExampleDatabase implements DatabaseInterface {
    private final HashMap<UUID, User> users;
    private final HashMap<UUID, Photograph> photos;
    
    public BadExampleDatabase(){
        users = new HashMap<>();
        photos = new HashMap<>();
        
        //storeUser(new User("John Doe", "johndoe@nonexistant.com", UUID.randomUUID() ));
    }

    @Override
    public void storeUser(User user) {
        throw new UnsupportedOperationException();
        //users.put(user.getId(), user);
    }

    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public void storePhotograph(Photograph photo) {
        throw new UnsupportedOperationException();
        //photos.put(photo.getId(), photo);
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
    
    @Override
    public List<String> getAllCategories() {
        return photos.values().stream().flatMap((photograph)->{
            return photograph.getCategories().stream();
        }).map((catName)->{
            return catName.toUpperCase();
        }).distinct().collect(Collectors.toList());
    }

    @Override
    public Photograph getPhotograph(String id, boolean withWatermark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
