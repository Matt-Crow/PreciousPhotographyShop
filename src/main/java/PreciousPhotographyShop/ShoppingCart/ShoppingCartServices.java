package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.photographs.PhotographRepository;
import PreciousPhotographyShop.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingCartServices {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PhotographRepository photographRepository;

    public List<CartItem> listCartItems(UserEntity userEntity) {
        return cartItemRepository.findByUserEntity(userEntity);
    }

    public int addProduct(String photoId, int quantity, UserEntity user){
        int addedQuantity = quantity;

        PhotographEntity photo = photographRepository.findById(photoId).get();

        CartItem cartItem = cartItemRepository.findByUserEntityAndPhotographEntity(user, photo);

        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }
        else{
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setUserEntity(user);
            cartItem.setPhotographEntity(photo);
        }
        cartItemRepository.save(cartItem);
        return addedQuantity;
    }

    public double updateQuantity(String photoId, int quantity, UserEntity user) {
        cartItemRepository.updateQuantity(quantity, photoId, user.getId());
        PhotographEntity photo = photographRepository.findById(photoId).get();
        double subtotal = photo.getPrice() * quantity;
        return subtotal;
    }

    public void removeProduct(String photoId, UserEntity user){
        cartItemRepository.deleteByUserEntityAndPhotographEntity(user.getId(), photoId);
    }
}