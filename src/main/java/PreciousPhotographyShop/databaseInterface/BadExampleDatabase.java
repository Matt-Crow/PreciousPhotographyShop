package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;
import java.util.HashMap;

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
        
        storeUser(new User("John Doe", "johndoe@nonexistant.com"));
    }

    @Override
    public void storeUser(User user) {
        users.put(user.getName().toLowerCase(), user);
    }

    @Override
    public User getUser(String name) {
        return users.get(name.toLowerCase());
    }

    @Override
    public void storePhotograph(Photograph photo) {
        photos.put(photo.getName().toLowerCase(), photo);
    }

    @Override
    public Photograph getPhotograph(String name, boolean withWatermark) {
        // todo: do stuff if withWatermark is true
        return photos.get(name.toLowerCase());
    }
}
