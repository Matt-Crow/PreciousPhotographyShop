package PreciousPhotographyShop.ShoppingCart;

import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Table(name = "Cart_Items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @Column(name = "photo_id")
    private PhotographEntity photographEntity;

    @ManyToOne
    @Column(name = "user_id")
    private UserEntity userEntity;

    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhotographEntity getPhotographEntity() {
        return photographEntity;
    }

    public void setPhotographEntity(PhotographEntity photographEntity) {
        this.photographEntity = photographEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

