package PreciousPhotographyShop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


/**
 *
 * @author Matt
 */


public class User {

    private final String name;
    private final String email;
    private final UUID id;
    
    public User(String name, String email, UUID id){
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
    
    public final UUID getId(){
        return id;
    }
}
