package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Matt
 */
public class BadExampleDatabase implements DatabaseInterface {
    private final HashMap<String, User> users;
    private final HashMap<String, Photograph> photos;
    
    public BadExampleDatabase(){
        users = new HashMap<>();
        photos = new HashMap<>();
        
        storeUser(new User("John Doe", "johndoe@nonexistant.com", "John Doe"));
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
    public Photograph getPhotograph(String id, boolean withWatermark) {
        // todo: do stuff if withWatermark is true
        return photos.get(id);
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        return photos.values().stream().filter((photo)->{
            return Arrays.stream(categories).anyMatch(photo::isInCategory);
        }).toArray((size)->new Photograph[size]);
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
