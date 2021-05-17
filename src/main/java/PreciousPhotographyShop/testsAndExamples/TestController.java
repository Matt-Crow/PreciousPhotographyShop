package PreciousPhotographyShop.testsAndExamples;

import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import java.util.Set;
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
    
    @Autowired private CategoryRepository catRepo;
    
    //@GetMapping("/")
	public String index(@RequestParam(name="name", required=false, defaultValue="Someone") String name, Model model) {
        model.addAttribute("name", name);
        
        /*
        Generally speaking, static references are
        bad, but I think this is the only way to
        do it.
        */
        UserEntity theUser = null;
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
        
		return "index-2";
	}
    
    @GetMapping("/testCategories")
    public @ResponseBody String testCats(){
        CategoryEntity animal = new CategoryEntity("Animal");
        CategoryEntity catCat = new CategoryEntity("Cat", animal);
        this.catRepo.save(animal);
        this.catRepo.save(catCat);
        
        StringBuilder sb = new StringBuilder();
        Set<String> cats = this.databaseInterface.getAllCategories();
        for(String cat : cats){
            sb.append(cat).append("</br>");
            for(PhotographEntity photo : this.databaseInterface.getPhotographsByCategory(cat)){
                sb.append(String.format("* %s</br>", photo.getName()));
            }
        }
        return sb.toString();
    }
    
    @GetMapping("/main")
    public @ResponseBody String main(){
        StringBuilder sb = new StringBuilder();
        this.databaseInterface.getAllPhotoIds().stream().forEach((id)->{
            try {
                this.databaseInterface.getPhotograph(id, true);
            } catch(Exception ex){
                sb.append(String.format("Need to delete %s</br>", id));
                this.databaseInterface.deletePhotoByID(id);
                //System.out.printf("delete %s\n", id);
            }
        });
        
        return sb.toString();
    }
}