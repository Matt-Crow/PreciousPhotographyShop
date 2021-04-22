package PreciousPhotographyShop.index;

import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.photographs.PhotographRepository;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The IndexController handles requests made on the home page of the website
 * @author Matt Crow
 */

@Controller
public class IndexController {
    
    @Autowired private DatabaseInterface db;
    
    @Autowired private CategoryRepository catRepo;
    @Autowired private PhotographRepository photoRepo;
    
    @GetMapping({"/", "/index"})
    public String index(){
        return "index-2";
    }
    
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String search(
        @RequestBody MultiValueMap<String, String> formData,
        Model model
    ){
        formData.forEach((s, list)->{
            System.out.println(s + ":");
            list.forEach(System.out::println);
        });
        
        LinkedList<String> foundUrls = new LinkedList<>();
        formData.get("search").forEach((String data)->{
            catRepo.findAllByNameContainingIgnoreCase(data).forEach((cat)->{
                foundUrls.add(String.format("/allPhotos?category=%s", cat.getName()));
            });
            photoRepo.findAllByNameContainingIgnoreCase(data).forEach((photo)->{
                foundUrls.add(String.format("/viewPhoto?id=%s", photo.getId()));
            });
            photoRepo.findAllByDescriptionContainingIgnoreCase(data).forEach((photo)->{
                foundUrls.add(String.format("/viewPhoto?id=%s", photo.getId()));
            });
            // todo add user search
        });
        model.addAttribute("found", foundUrls);
        
        return "search";
    }
}
