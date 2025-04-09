package com.uade.tpo.marketplace.entity.dto;

import com.uade.tpo.marketplace.entity.Product;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long id; 
    private ProductRequest product;  //private Long productId; Se tiene que "llamar" a la clase producto
    private int quantity;
    private float price;
    private CartRequest cart; // Opcional
}
