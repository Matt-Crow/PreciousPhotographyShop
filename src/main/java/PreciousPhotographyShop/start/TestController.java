package PreciousPhotographyShop.start;

import PreciousPhotographyShop.Main;
import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * This TestController serves as a general example of how Spring works
 * @author Matt Crow
 */

@Controller
public class TestController {
    
    @Autowired
    private DatabaseInterface databaseInterface;
    
    @GetMapping("/")
	public String index(@RequestParam(name="name", required=false, defaultValue="Someone") String name, Model model) {
        model.addAttribute("name", name);
        
        /*
        Generally speaking, static references are
        bad, but I think this is the only way to
        do it.
        */
        User theUser = null;
        try {
            theUser = Main.DATABASE.getUser(name);
        } catch(NullPointerException ex){
            System.err.printf("Couldn't find user with name \"%s\"\n", name);
        }
        
        if(theUser == null){
            model.addAttribute("email", "Unknown");
        } else {
            model.addAttribute("email", theUser.getEmail());
        }
        
		return "myWebpage";
	}
    
    @GetMapping("/newPhoto")
    public String newPhotoForm(Model model){
        List<String> categoryNames = databaseInterface.getAllCategories();
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
        System.out.println("received form");
        try {
            MultipartFile file = photoFormResp.getFile();
            String name = photoFormResp.getName();
            List<String> categories = photoFormResp.getCategories();
            
            BufferedImage buff = ImageIO.read(file.getInputStream());
            Photograph photo = new Photograph(
                name,
                buff,
                null,
                categories.toArray(new String[categories.size()])
            );
            databaseInterface.storePhotograph(photo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/";
    }
    
    @GetMapping("/main")
    public @ResponseBody String main(){
        /*
        Testing all database capabilities
        */
        
        // Create user
        User u = new User("Fakey McDon'texist", "fakey@aol.com", null);
        try {
            databaseInterface.storeUser(u);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        // Get user
        try {
            u = databaseInterface.getUser(u.getId());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        // Create photograph
        Photograph p = null;
        try {
            BufferedImage buff = ImageIO.read(new File("C:\\Users\\Matt\\Pictures\\batmanParametric.jpg"));
            p = new Photograph("Test photo", buff, null, new String[]{"test"});
            databaseInterface.storePhotograph(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Retrieve photograph
        try {
            p = databaseInterface.getPhotograph(p.getId(), true);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        
        return Arrays.toString(databaseInterface.getPhotographsByCategory(new String[]{"test"}));
    }
}