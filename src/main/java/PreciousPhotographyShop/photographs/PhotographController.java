package PreciousPhotographyShop.photographs;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
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
            String name = photoFormResp.getName();
            List<String> categories = photoFormResp.getCategories();
            
            BufferedImage buff = ImageIO.read(file.getInputStream());
            PhotographEntity photo = new PhotographEntity();
            photo.setName(name);
            photo.setPhoto(buff);
            photo.setDescription("no description");
            photo.setPrice(20.0); // todo set price
            photo.setCategoryNames(categories.stream().collect(Collectors.toSet()));
            photo.setIsRecurring(false); // todo set recurring
            
            databaseInterface.storePhotograph(photo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/";
    }
    
    @GetMapping("/allPhotos")
    public String allPhotos(Model model){
        //temp
        model.addAttribute("photos", this.databaseInterface.getAllPhotoIds());
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
        return "viewPhoto";
    }
}
