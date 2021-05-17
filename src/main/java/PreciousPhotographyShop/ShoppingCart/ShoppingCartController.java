package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.security.LoginService;
import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.users.UserService;
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
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cart")
    public List<CartItem> showShoppingCart() {
        UserEntity userEntity = userService.getLoggedInUser();
        List<CartItem> cartItems = cartServices.listCartItems(userEntity);
        return cartItems;
    }
}
