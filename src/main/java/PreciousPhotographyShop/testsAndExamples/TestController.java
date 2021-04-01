package PreciousPhotographyShop.testsAndExamples;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.users.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
            theUser = databaseInterface.getUser(name);
        } catch(Exception ex){
            System.err.printf("Couldn't find user with name \"%s\"\n", name);
        }
        
        if(theUser == null){
            model.addAttribute("email", "Unknown");
        } else {
            model.addAttribute("email", theUser.getEmail());
        }
        
		return "index";
	}

	/*
    @GetMapping("/main")
    public @ResponseBody String main(){
        /*
        Testing all database capabilities

        
        // Create user
        User u = new User("Fakey McDon'texist", "fakey@aol.com");
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
            p = new Photograph("Test photo", buff, new String[]{"test"});
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
    */
}