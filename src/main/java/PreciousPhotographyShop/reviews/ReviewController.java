package PreciousPhotographyShop.reviews;

import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.security.LoginService;
import PreciousPhotographyShop.users.UserEntity;
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
    
    @Autowired
    private LoginService loginService;
    
    /*
    Reviews for photographs
    */
    
    @GetMapping("/newPhotoReview")
    public String iWantToRoastSomething(@RequestParam(name="id") String whatToRoast, Model model) throws Exception{
        UserEntity reviewer = loginService.getLoggedInUser();
        
        ReviewEntity theReview = new ReviewEntity();
        /*
        Important: setting attributes in theReview will not carry over to what
        the next method sees. For example, setting theReview's ID here will not
        set thee ID of the ReviewEntity received by the next method, as that ID
        will not be contained in the form response sent to it.
        */
        model.addAttribute("review", theReview);
        model.addAttribute("reviewer", reviewer.getId());
        model.addAttribute("reviewingId", whatToRoast);
        model.addAttribute("reviewing", database.getPhotograph(whatToRoast, true).getName());
        model.addAttribute("type", "photo");
        return "newReview";
    }
    
    @PostMapping("/newPhotoReview")
    public String somethingWasRoasted(
        @ModelAttribute ReviewEntity theRoast,
        @RequestParam(name="id") String whatWasRoasted,
        @RequestParam(name="reviewer") String whoRoasted,
        @RequestParam(name="type") String photoOrSeller
    ){
        try {
            theRoast.setReviewerId(whoRoasted);
            theRoast.setReviewedId(whatWasRoasted);
            System.out.println(theRoast.toString());
            reviews.save(theRoast);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        String redirectUrl = null;
        if(photoOrSeller.equals("photo")){
            redirectUrl = String.format("redirect:/viewPhoto?id=%s", whatWasRoasted);
        } else {
            redirectUrl = String.format("redirect:/seller/sellerPage?id=%s", whatWasRoasted);
        }
        return redirectUrl; 
    }
    
    @GetMapping("/newSellerReview")
    public String getSellerReviewForm(
        @RequestParam(value="id", required=true) String sellerId,
        Model model
    ) throws Exception {
        UserEntity reviewer = loginService.getLoggedInUser();
        model.addAttribute("review", new ReviewEntity());
        model.addAttribute("reviewer", reviewer.getId());
        model.addAttribute("reviewingId", sellerId);
        model.addAttribute("reviewing", database.getUser(sellerId).getUsername());
        model.addAttribute("type", "seller");
        return "newReview";
    }
}
