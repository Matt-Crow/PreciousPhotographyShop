package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.temp.LoginService;
import PreciousPhotographyShop.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartServices cartServices;

    @Autowired
    private LoginService loginService;

    @PostMapping("/cart/add/{photoid}/{qty}")
    public String addProductToCart(@PathVariable("photoid") String photoId, @PathVariable("qty") int quantity){

        UserEntity user = loginService.getLoggedInUser();

        if (user == null) {
            return "You must login in order to add products to your shopping cart.";
        }
        int addedQuantity = cartServices.addProduct(photoId, quantity, user);

        System.out.println("Added item");

        double subtotal = cartServices.updateQuantity(photoId, quantity, user);

        return String.valueOf(subtotal);
    }
}
