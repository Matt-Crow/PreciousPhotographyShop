package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.temp.LoginService;
import PreciousPhotographyShop.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServices cartServices;

    @Autowired
    private LoginService loginService;

    @GetMapping("/cart")
    public String showShoppingCart() {
        UserEntity userEntity = loginService.getLoggedInUser();
        List<CartItem> cartItems = cartServices.listCartItems(userEntity);
        return "shopping_cart";
    }
}
