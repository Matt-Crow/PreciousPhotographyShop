package PreciousPhotographyShop.shoppingCart;

import PreciousPhotographyShop.photographs.PhotographEntity;

import javax.persistence.*;

@Entity
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
