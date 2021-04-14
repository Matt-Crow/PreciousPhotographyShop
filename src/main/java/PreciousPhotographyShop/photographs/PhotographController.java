package PreciousPhotographyShop.photographs;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.reviews.ReviewRepository;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/newPhoto")
    public String newPhotoForm(Model model){
        Set<String> categoryNames = databaseInterface.getAllCategories();
        categoryNames.add("still life");
        categoryNames.add("animals");
        categoryNames.add("black and white");
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("formResponse", new PhotoFormResponse());
        return "postPhotographFormPage";
    }
    
    @PostMapping("/testPostPhoto")
    public String postNewPhoto(
        @ModelAttribute PhotoFormResponse photoFormResp,
        Model model
    ){
        System.out.println("received form: todo get logged in user");
        try {
            MultipartFile file = photoFormResp.getFile();
            List<String> categories = photoFormResp.getCategories();
            
            BufferedImage buff = ImageIO.read(file.getInputStream());
            PhotographEntity photo = (PhotographEntity)photoFormResp;
            photo.setPhoto(buff);
            photo.setCategoryNames(categories.stream().collect(Collectors.toSet()));
            photo.setIsRecurring(false); // todo set recurring
            System.out.println(photo);
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
        System.out.printf("All photos by category is %s\n", category);
        
        model.addAttribute(
            "categoryNames", 
            databaseInterface.getAllCategories().stream().collect(Collectors.toList())
        );
        
        if(category == null){
            model.addAttribute("photos", this.databaseInterface.getAllPhotoIds());
        } else {
            model.addAttribute(
                "photos", 
                this.databaseInterface.getPhotographsByCategory(category).stream().map((photo)->{
                   return photo.getId(); 
                }).collect(Collectors.toList())
            );
        }
        return "allPhotos";
    }
    
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
    public String viewPhoto(@RequestParam String id, Model model){
        PhotographEntity photo = this.databaseInterface.getPhotograph(id, true);
        // todo error handling
        model.addAttribute("photo", photo);
        model.addAttribute(
            "reviews", 
            reviewRepository.findAllByReviewedId(id)
        );
        return "viewPhoto";
    }
}
