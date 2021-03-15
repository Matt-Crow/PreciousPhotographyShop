package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * Notice how this specifies WHAT the system can do, not HOW it does it.
 * 
 * @author Matt Crow
 */
public interface DatabaseInterface {
    public void storeUser(User user);
    public User getUser(String id);
    public void storePhotograph(Photograph photo);
    public Photograph getPhotograph(UUID id, boolean withWatermark);
    public Photograph getPhotograph(UUID id);
    public Photograph[] getPhotographsByCategory(String[] categories);

    // Temporary method added by Daniel R
    public HashMap<UUID, Photograph> getAllPhotos();
    public int deletePhotoByID(UUID id);
    public int updatePhotoByID(UUID id, Photograph photograph);

    }


