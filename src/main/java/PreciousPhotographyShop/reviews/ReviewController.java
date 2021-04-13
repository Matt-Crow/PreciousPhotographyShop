package PreciousPhotographyShop.reviews;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Matt
 */

@Controller
@RequestMapping("reviews")
public class ReviewController {
    @Autowired
    private DatabaseInterface database;
    
    @Autowired
    private ReviewRepository reviews;
    
    @GetMapping("/newReview")
    public String iWantToRoastSomeone(@RequestParam(name="id") String whatToRoast, Model model){
        ReviewEntity theReview = new ReviewEntity();
        theReview.setReviewerId("lol idk");// todo set reviewerId
        theReview.setReviewedId(whatToRoast); // not saving in the next method
        model.addAttribute("review", theReview);
        return "newReview";
    }
    
    @PostMapping("/newReview")
    public String someoneWasRoasted(@ModelAttribute ReviewEntity theRoast){
        try {
            System.out.println(theRoast.toString());
            reviews.save(theRoast);
            System.out.println(theRoast.toString());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return "allPhotos"; // how to redirect to other controller?
    }
}
