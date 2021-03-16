package PreciousPhotographyShop.model;

/**
 *
 * @author Matt Crow
 */
public class User {
    private final String name;
    private final String email;
    private String id;
    
    public User(String name, String email, String id){
        this.name = name;
        this.email = email;
        this.id = id;
    }
    
    public final String getName(){
        return name;
    }
    
    public final String getEmail(){
        return email;
    }
    
    public final String getId(){
        return id;
    }
    
    // needed for after returning from DB
    public final void setId(String id){
        this.id = id;
    }
}
