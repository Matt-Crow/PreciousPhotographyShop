package PreciousPhotographyShop.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Matt
 */
@Controller
public class SellerController {
    @GetMapping("/seller")
    public String sellerPage(
        @RequestParam(name="id", required=true) String id,
        Model model
    ) {
        
        return "seller";
    }
}
