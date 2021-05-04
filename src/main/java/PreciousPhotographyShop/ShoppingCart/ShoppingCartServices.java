package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServices {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> listCartItems(UserEntity userEntity) {
        return cartItemRepository.findByUserEntity(userEntity);
    }
}
