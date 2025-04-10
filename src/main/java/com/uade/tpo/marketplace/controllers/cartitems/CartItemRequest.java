package com.uade.tpo.marketplace.controllers.cartitems;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.Product;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long id; 
    private Product product;  //private Long productId; Se tiene que "llamar" a la clase producto
    private int quantity;
    private float price;
    private Cart cart; // Opcional
}
