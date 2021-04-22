package PreciousPhotographyShop.index;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
    
    @Autowired
    private DatabaseInterface db;
    
    @GetMapping({"/", "/index"})
    public String index(){
        return "index-2";
    }
    
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String search(
        @RequestBody MultiValueMap<String, String> formData
    ){
        formData.forEach((s, list)->{
            System.out.println(s + ":");
            list.forEach(System.out::println);
        });
        return "search";
    }
}
