package PreciousPhotographyShop.photographs;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.reviews.ReviewEntity;
import PreciousPhotographyShop.reviews.ReviewRepository;
import PreciousPhotographyShop.reviews.ReviewWidgetInfo;
import PreciousPhotographyShop.temp.BadLoginService;
import PreciousPhotographyShop.users.UserEntity;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PhotographController {
    @Autowired
    private DatabaseInterface databaseInterface;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private BadLoginService loginService;
    
    @Autowired
    private PhotoService photoService;
    
    @GetMapping("/newPhoto")
    public String newPhotoForm(Model model){
        String userId = loginService.getLoggedInUser().getId();
        Set<String> categoryNames = databaseInterface.getAllCategories();
        categoryNames.add("still life");
        categoryNames.add("animals");
        categoryNames.add("black and white");
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("formResponse", new PhotoFormResponse());
        model.addAttribute("sellerId", userId);
        return "postPhotographFormPage";
    }
    
    @PostMapping("/newPhoto")
    public String postNewPhoto(
        @ModelAttribute PhotoFormResponse photoFormResp,
        @RequestParam(name="sellerId") String sellerId,
        Model model
    ){
        try {
            MultipartFile file = photoFormResp.getFile();
            List<String> categories = photoFormResp.getCategories();
            
            BufferedImage buff = ImageIO.read(file.getInputStream());
            PhotographEntity photo = photoFormResp.getContainedEntity();
            photo.setOwnerId(sellerId);
            photo.setPhoto(buff);
            photo.setCategoryNames(categories.stream().collect(Collectors.toSet()));
            photo.setIsRecurring(false); // todo set recurring
            photo.setPostedDate(new Date());
            databaseInterface.storePhotograph(photo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/";
    }
    
    @GetMapping("/allPhotos")
    public String allPhotos(
        @RequestParam(name="category", required = false) String category, 
        Model model
    ){
        model.addAttribute(
            "categoryNames", 
            databaseInterface.getAllCategories().stream().collect(Collectors.toList())
        );
        
        if(category == null){
            //model.addAttribute("photos", this.databaseInterface.getAllPhotoIds());
            model.addAttribute("photos", photoService.getBrowseWidgetsForAllPhotos());
        } else {
            model.addAttribute("photos", photoService.getBrowseWidgetsByCategory(category));
            /*
            model.addAttribute(
                "photos", 
                this.databaseInterface.getPhotographsByCategory(category).stream().map((photo)->{
                   return photo.getId(); 
                }).collect(Collectors.toList())
            );*/
        }
        return "allPhotos";
    }
    
    /**
     * Use this method to retrieve the image data for a photograph like so:
     * <img th:src="@{'/photo?id=' + ${photoId}}"/>
     * 
     * @param id the ID of the photo to get
     * 
     * @return the binary image data, with the watermark attached 
     */
    @GetMapping("/photo")
    public @ResponseBody byte[] photo(@RequestParam String id){
        byte[] ret = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage buff = this.databaseInterface.getPhotograph(id, true).getPhoto();
            ImageIO.write(buff, "jpg", baos);
            ret = baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
    
    @GetMapping("/viewPhoto")
    public String viewPhoto(
        @RequestParam String id, 
        Model model
    ){
       
        PhotographEntity photo = null;
        try {
            photo = this.databaseInterface.getPhotograph(id, true);
        } catch(Exception ex){
            System.err.printf("failed to load photo with id \"%s\".\n", id);
            return "redirect:/allPhotos";
        }
        
        model.addAttribute("photo", photo);
        try{
            UserEntity seller = this.databaseInterface.getUser(photo.getOwnerId());
            model.addAttribute("sellerName", seller.getUsername());
        } catch(Exception ex){
            System.err.printf("Photo with id \"%s\" and name \"%s\" has no seller.\n", id, photo.getName());
            model.addAttribute("sellerName", "no seller specified");
        }
        
        List<ReviewWidgetInfo> reviews = new LinkedList<>();
        reviewRepository.findAllByReviewedId(id).forEach((ReviewEntity asEntity)->{
            reviews.add(new ReviewWidgetInfo(
                this.databaseInterface.getUser(asEntity.getReviewerId()).getUsername(),
                asEntity.getText(),
                asEntity.getRating()
            ));
        });
        
        model.addAttribute(
            "reviews", 
            reviews
        );
        return "viewPhoto";
    }
}
