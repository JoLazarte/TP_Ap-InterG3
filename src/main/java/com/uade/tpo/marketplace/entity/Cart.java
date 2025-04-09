package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL) //Cada usuario-comprador tiene un solo carrito. Puede tener varias compras, pero un solo carrito a la vez
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_id")
    private Buy buy;

    @Column
    private float total;
}
