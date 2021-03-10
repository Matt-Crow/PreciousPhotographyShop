package PreciousPhotographyShop.model;

/**
 *
 * @author Matt
 */
public class User {
    private final String name;
    private final String email;
    
    public User(String name, String email){
        this.name = name;
        this.email = email;
    }
    
    public final String getName(){
        return name;
    }
    
    public final String getEmail(){
        return email;
    }
}
