package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    public List<CartItem> findByUserEntity(UserEntity userEntity);
}
