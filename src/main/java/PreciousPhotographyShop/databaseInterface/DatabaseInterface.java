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
    /*
    User related methods
    */
    
    /**
     * 
     * @param user the new user to store in the database
     * 
     * @return the user's ID in the database 
     */
    public String storeUser(User user);
    
    /**
     * 
     * @param id the ID of the user to get
     * 
     * @return the user with the given ID. May return null or throw an exception
     * if none exists.
     */
    public User getUser(String id);
    
    
    /*
    Photograph related methods
    */
    
    /**
     * 
     * @param photo the new photograph to store in the database
     * @return the photograph's ID in the database
     */
    public String storePhotograph(Photograph photo);
    
    /**
     * 
     * @param id the ID of the photograph to get
     * @param withWatermark whether or not to attach a watermark to the returned
     *  photograph's image.
     * 
     * @return the photograph with the given ID. May return null or throw an
     *  Exception if none exists.
     */
    public Photograph getPhotograph(String id, boolean withWatermark);
    
    // Temporary method added by Daniel R
    public int deletePhotoByID(String id);
    
    // Temporary method added by Daniel R
    public int updatePhotoByID(String id, Photograph photograph);
    
    public Photograph[] getPhotographsByCategory(String[] categories);

    // Temporary method added by Daniel R
    // Matt: probably a List or HashSet of Photographs is all we need this to return
    public HashMap<String, Photograph> getAllPhotos();
    
    //temp
    public List<String> getAllPhotoIds();
    public List<String> getAllCategories();
}