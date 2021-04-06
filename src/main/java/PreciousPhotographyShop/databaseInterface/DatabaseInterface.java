package PreciousPhotographyShop.databaseInterface;


import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

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
    public String storeUser(UserEntity user);
    
    /**
     * 
     * @param id the ID of the user to get
     * 
     * @return the user with the given ID. May return null or throw an exception
     * if none exists.
     */
    public UserEntity getUser(String id);
    
    
    /*
    Photograph related methods
    */
    
    /**
     * 
     * @param photo the new photograph to store in the database
     * @return the photograph's ID in the database
     */
    public String storePhotograph(PhotographEntity photo);
    
    /**
     * 
     * @param id the ID of the photograph to get
     * @param withWatermark whether or not to attach a watermark to the returned
     *  photograph's image.
     * 
     * @return the photograph with the given ID. May return null or throw an
     *  Exception if none exists.
     */
    public PhotographEntity getPhotograph(String id, boolean withWatermark);
    
    // Temporary method added by Daniel R
    public int deletePhotoByID(String id);
    
    // Temporary method added by Daniel R
    public int updatePhotoByID(String id, PhotographEntity photograph);
    
    /*
    Category methods
    */
    
    /**
     * returns all Photographs belonging to the category with the given name.
     * @param categoryName the name of the category to get photographs from, <b>
     * or null to get all photographs</b>
     * 
     * @return the photographs belonging to the given category 
     */
    public Set<PhotographEntity> getPhotographsByCategory(String categoryName);
    
    public Set<String> getAllCategories();

    
    // Temporary method added by Daniel R
    // Matt: probably a List or HashSet of Photographs is all we need this to return
    public HashMap<String, PhotographEntity> getAllPhotos();
    
    //temp
    public List<String> getAllPhotoIds();
    
}