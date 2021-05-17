package PreciousPhotographyShop.users;

import PreciousPhotographyShop.databaseInterface.*;
import PreciousPhotographyShop.photographs.*;
import PreciousPhotographyShop.reviews.ReviewService;
import PreciousPhotographyShop.security.LoginService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * I'm currently just using this class instead of the UserController to 
 * distinguish between API and non-API controllers.
 * 
 * @author Matt Crow
 */
@Controller
@RequestMapping("seller")
public class SellerController {
    private final LoginService login;
    private final DatabaseInterface db;
    private final PhotographRepository photos;
    private final PhotoService photoService;
    private final ReviewService reviews;
    private final UserService users;
    private final UserRepository userRepository;
    
    public SellerController(LoginService login, DatabaseInterface db, PhotographRepository photos, PhotoService photoService, ReviewService reviews, UserService users, UserRepository userRepository){
        this.login = login;
        this.db = db;
        this.photos = photos;
        this.photoService = photoService;
        this.reviews = reviews;
        this.users = users;
        this.userRepository = userRepository;
    }
    
    @GetMapping("/yourSellerPage")
    public String getYourSellerPage(){
        return String.format("redirect:/seller/sellerPage?id=%s", login.getLoggedInUser().getId());
    }
    
    @GetMapping("/sellerPage")
    public String sellerPage(
        @RequestParam(name="id", required=true) String id,
        Model model
    ) {
        if(isLoggedIn(id)){
            model.addAttribute("canEdit", true);
        }
        
        String viewName = "seller/sellerPage"; //"seller/Account";
        try {
            UserEntity user = db.getUser(id);
            model.addAttribute("seller", user);
            // get their 4 most recent
            List<BrowsePhotoWidgetInfo> mostRecent = new LinkedList<>();
            Iterator<PhotographEntity> it = photos.findAllByOwnerIdOrderByPostedDateDesc(id).iterator();
            for(int i = 0; i < 4 && it.hasNext(); i++){
                mostRecent.add(photoService.mapPhotoToBrowseWidget(it.next()));
            }
            model.addAttribute("photos", mostRecent);
            model.addAttribute("reviews", reviews.getReviewWidgetsFor(user));
        } catch (Exception ex) {
            System.err.printf("Couldn't get user with ID of \"%s\"\n", id);
            ex.printStackTrace();
            viewName = "redirect:/";
        }
        return viewName;
    }
    
    // Steven's template. I couldn't get this working
    @GetMapping("/sellerPage2")
    public String sellerPage2(
        @RequestParam(name="id", required=true) String id,
        Model model
    ) {
        if(isLoggedIn(id)){
            model.addAttribute("canEdit", true);
        }
        
        String viewName = "seller/Account";
        try {
            UserEntity user = db.getUser(id);
            model.addAttribute("seller", user);
            // get their 4 most recent
            List<BrowsePhotoWidgetInfo> mostRecent = new LinkedList<>();
            Iterator<PhotographEntity> it = photos.findAllByOwnerIdOrderByPostedDateDesc(id).iterator();
            for(int i = 0; i < 4 && it.hasNext(); i++){
                mostRecent.add(photoService.mapPhotoToBrowseWidget(it.next()));
            }
            model.addAttribute("photos", mostRecent);
            model.addAttribute("reviews", reviews.getReviewWidgetsFor(user));
        } catch (Exception ex) {
            System.err.printf("Couldn't get user with ID of \"%s\"\n", id);
            ex.printStackTrace();
            viewName = "redirect:/";
        }
        return viewName;
    }
    
    @GetMapping("editSellerPage")
    public String getEditSellerPage(@RequestParam("sellerId") String sellerId, Model model){
        String url = "seller/editSellerPage";
        try {
            if(!isLoggedIn(sellerId)){
                throw new Exception("Only the seller can edit their own seller page");
            }
            model.addAttribute("sellerId", sellerId);
            model.addAttribute("sellerPageInfo", getInfoFor(sellerId));
        } catch (Exception ex) {
            ex.printStackTrace();
            url = String.format("redirect:/seller/sellerPage?id=%s", sellerId);
        }
        return url;
    }
    
    private SellerPageInfo getInfoFor(String sellerId) throws Exception{
        UserEntity user = db.getUser(sellerId);
        SellerPageInfo info = new SellerPageInfo(user);
        List<PhotographEntity> theirPhotos = user.getPhotoIds().stream().map((photoId)->{
            PhotographEntity pe = null;
            try {
                pe = this.photoService.getPhotoByID(photoId);
            } catch(Exception ex){
                System.err.printf("Failed to get photo with ID %s", photoId);
            }
            return pe;
        }).filter((pe)->pe != null).collect(Collectors.toList());
        info.setPhotos(theirPhotos);
        return info;
    }
    
    @PostMapping("updateSellerInfo")
    public String postEditSellerPage(
        @RequestParam("sellerId") String sellerId,
        @ModelAttribute() SellerPageInfo updatedInfo
    ){
        if(isLoggedIn(sellerId)){
            try {
                // only update if the post request was made for the logged in user
                // prevents sellers from manipulating other sellers' info
                users.updateUser(sellerId, updatedInfo);
                //updateUser(sellerId, updatedInfo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }        
        return String.format("redirect:/seller/sellerPage?id=%s", sellerId);
    }
    
    /*
    While this should be stored in the UserService, I had an issue where the 
    UserService passed into this' constructor would have all of its fields set
    to null. The only difference I could find between this UserService is how
    it says something involving "enchancerbyspringcglib" in the UserService
    field of this class. Not sure why it wasn't wiring up properly
    
    It looks like the UserService works now, but I'm not sure why
    */
    private void updateUser(String id, SellerPageInfo newInfo){
        UserEntity user = userRepository.findById(id).get();
        user.setUsername(newInfo.getUsername());
        user.setDescription(newInfo.getDescription());
        if(newInfo.isProfilePictureIdSet()){
            user.setProfilePictureId(newInfo.getProfilePictureId());
        } else {
            user.setProfilePictureId(null); // sets back to default profile pic
        }
        if(newInfo.isPasswordSet()){
            throw new UnsupportedOperationException("Changing password not supported yet");
        }
        userRepository.save(user); // update the user in the database
    }
    
    /**
     * Use this method to retrieve the image data for a user's profile picture 
     * like so:
     * <img th:src="@{'/profilePicture?id=' + ${userId}}"/>
     * 
     * If no image is found, returns a default image
     * 
     * @param id the ID of the user to get the profile picture for
     * 
     * @return the binary image data, with the watermark attached 
     */
    @GetMapping("/profilePicture")
    public @ResponseBody byte[] profilePicture(@RequestParam(name="id", required=true) String id){
        byte[] ret = null;
        UserEntity user = null;
        try {
            user = this.db.getUser(id);
        } catch(Exception ex){
            System.err.printf("Error retrieving user with ID %s\n", id);
            ex.printStackTrace();
        }
        
        if(user == null || user.getProfilePictureId() == null){
            ret = this.getDefaultProfilePic();
        } else {
            ret = this.getProfilePic(user.getProfilePictureId());
        }
        
        return ret;
    }
    
    private byte[] getDefaultProfilePic(){
        byte[] ret = null;
        try (
            InputStream in = SellerController.class.getResourceAsStream("/blankUserImage.jpg");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ) {
            BufferedImage buff = ImageIO.read(in);
            ImageIO.write(buff, "jpg", bout);
            ret = bout.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
    
    private byte[] getProfilePic(String id){
        byte[] ret = null;
        try (
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ) {
            BufferedImage buff = LocalFileSystem.getInstance().load(id, true);
            ImageIO.write(buff, "jpg", bout);
            ret = bout.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
    
    private boolean isLoggedIn(String sellerId){
        UserEntity user = this.login.getLoggedInUser();
        return user != null && user.getId().equals(sellerId);
    }
}
