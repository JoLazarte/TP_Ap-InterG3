
package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductCatalog product;

    @Column
    private int quantity;

    public float calculateTotal() {
        return product.getPrice() * quantity;
    }
}