package PreciousPhotographyShop.index;

import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The IndexController handles requests made on the home page of the website
 * @author Matt Crow
 */

@Controller
public class IndexController {
    
    @Autowired private SearchService searchService;
    
    @GetMapping({"/", "/index"})
    public String index(){
        return "index-2";
    }
    
    /**
     * 
     * @param searchTerms the search terms the user entered in the search bar.
     *  These are delimanated by spaces, so having searchTerms of "a b" counts
     *  as searching "a" or "b" and finding the intersection of the results.
     * 
     * @param model
     * 
     * @return
     * 
     * @throws UnsupportedEncodingException if any issues arise from decoding
     *  the search terms
     */
    @GetMapping(value = "/search")
    public String search(
        @RequestParam(name="search-terms") String searchTerms,
        Model model
    ) throws UnsupportedEncodingException {
        String[] terms = URLDecoder.decode(searchTerms, StandardCharsets.UTF_8.toString()).split(" ");
        LinkedList<SearchResultInfo> foundUrls = new LinkedList<>();
        
        Set<CategoryEntity> matchedCats = this.searchService.getMatchingCategories(terms);
        Set<PhotographEntity> matchedPhotos = this.searchService.getMatchingPhotos(terms);
        Set<UserEntity> matchedUsers = this.searchService.getMatchingUsers(terms);
        
        matchedCats.forEach((cat)->{
            foundUrls.add(resultFor(cat));
        });
        
        matchedPhotos.forEach((photo)->{
            foundUrls.add(resultFor(photo));
        });
        
        matchedUsers.forEach((user)->{
            foundUrls.add(resultFor(user));
        });
        
        model.addAttribute("found", foundUrls);
        return "search";
    }
    
    private SearchResultInfo resultFor(CategoryEntity cat){
        return new SearchResultInfo(
            String.format("Browse by category: %s", cat.getName()),
            String.format("/allPhotos?catgeory=%s", cat.getName())
        );
    }
    
    private SearchResultInfo resultFor(PhotographEntity photo){
        return new SearchResultInfo(
            String.format("Photograph: %s \n%s", photo.getName(), photo.getDescription()),
            String.format("/viewPhoto?id=%s", photo.getId())
        );
    }
    
    private SearchResultInfo resultFor(UserEntity user){
        return new SearchResultInfo(
            String.format("Seller: %s \n%s", user.getUsername(), user.getDescription()),
            String.format("/seller/sellerPage?id=%s", user.getId())
        );
    }
}
