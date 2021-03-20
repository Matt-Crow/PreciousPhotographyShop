package PreciousPhotographyShop.databaseInterface;


import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.users.User;
import java.util.List;

import java.util.HashMap;

/**
 * Notice how this specifies WHAT the system can do, not HOW it does it.
 * 
 * @author Matt Crow
 */
public interface DatabaseInterface {
    public void storeUser(User user);
    public User getUser(String id);
    public void storePhotograph(Photograph photo);
    public Photograph getPhotograph(String id, boolean withWatermark);
    public Photograph[] getPhotographsByCategory(String[] categories);


    // Temporary method added by Daniel R
    public HashMap<String, Photograph> getAllPhotos();
    public int deletePhotoByID(String id);
    public int updatePhotoByID(String id, Photograph photograph);

    public List<String> getAllCategories();
}