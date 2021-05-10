package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    public List<CartItem> findByUserEntity(UserEntity userEntity);

    public CartItem findByUserEntityAndPhotographEntity(UserEntity userEntity, PhotographEntity photographEntity);

    @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.photographEntity.id = ?2 AND c.userEntity.id = ?3")
    @Modifying
    public void updateQuantity(int quantity, String photoId, String userId);
}
