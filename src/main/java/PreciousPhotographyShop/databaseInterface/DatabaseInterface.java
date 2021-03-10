package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;

/**
 * Notice how this specifies WHAT the system can do, not HOW it does it.
 * 
 * @author Matt Crow
 */
public interface DatabaseInterface {
    public void storeUser(User user);
    public User getUser(String name);
    public void storePhotograph(Photograph photo);
    public Photograph getPhotograph(String name, boolean withWatermark);
}
