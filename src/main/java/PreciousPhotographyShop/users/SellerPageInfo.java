package PreciousPhotographyShop.users;

/**
 * Used for collecting form responses from editting a seller's page
 * @author Matt Crow
 */
public class SellerPageInfo {
    // SRS says users cannot change their email except by contacting an admin
    private String username;
    private String profilePictureId;
    private String description;
    private String password;
    
    public SellerPageInfo(){
        
    }
    
    public SellerPageInfo(UserEntity seller){
        this.username = seller.getUsername();
        this.profilePictureId = seller.getProfilePictureId();
        this.description = seller.getDescription();
        this.password = null; // don't set to user password!
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getProfilePictureId(){
        return profilePictureId;
    }
    
    public String getDescription(){
        return description;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setProfilePictureId(String profilePictureId){
        this.profilePictureId = profilePictureId;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public boolean isProfilePictureIdSet(){
        return profilePictureId != null;
    }
    
    public boolean isPasswordSet(){
        return password != null;
    }
}
