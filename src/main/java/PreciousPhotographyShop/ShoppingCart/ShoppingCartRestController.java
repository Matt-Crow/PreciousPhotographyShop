package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartServices cartServices;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/cart/add/{photoid}/{qty}")
    public String addProductToCart(@PathVariable("photoid") String photoId, @PathVariable("qty") int quantity){

//        UserEntity userEntity = userRepository.findUserEntityByEmail("deaht3099@gmail.com");
        UserEntity userEntity = userService.getLoggedInUser();

        if (userEntity == null) {
            return "You must login in order to add products to your shopping cart.";
        }
        int addedQuantity = cartServices.addProduct(photoId, quantity, userEntity);

        System.out.println("Added item");

        return addedQuantity + " item(s) of this product were added to your shopping cart";
    }

    @PostMapping("/cart/update/{pid}/{qty}")
    public String updateQuantity(@PathVariable("pid") String photoId, @PathVariable("qty") int quantity){

//        UserEntity userEntity = userRepository.findUserEntityByEmail("deaht3099@gmail.com");
        UserEntity userEntity = userService.getLoggedInUser();

        if (userEntity == null) {
            return "You must login in order to update the quantity of the products of your shopping cart.";
        }
        double subtotal = cartServices.updateQuantity(photoId, quantity, userEntity);

        return String.valueOf(subtotal);
    }

    @PostMapping("/cart/remove/{pid}")
    public String removePhotoFromCart(@PathVariable("pid") String photoId){

//        UserEntity userEntity = userRepository.findUserEntityByEmail("deaht3099@gmail.com");

        UserEntity userEntity = userService.getLoggedInUser();

        if (userEntity == null) {
            return "You must login in order to remove products from your shopping cart.";
        }
        cartServices.removeProduct(photoId, userEntity);

        return "The product has been removed from your shopping cart.";
    }
}