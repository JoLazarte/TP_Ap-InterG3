package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    // private Long id; // No hace falta?
    private Long productId;
    private int quantity;
    private float price;

    // private CartRequest cart; // Opcional
}