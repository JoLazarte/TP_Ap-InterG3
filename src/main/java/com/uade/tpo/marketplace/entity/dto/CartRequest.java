package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long id; // No hace falta
    private UserRequest buyer; 
    private float totalPrice;
}
