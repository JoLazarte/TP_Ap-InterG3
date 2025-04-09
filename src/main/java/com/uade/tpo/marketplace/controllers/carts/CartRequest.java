package com.uade.tpo.marketplace.controllers.carts;

import com.uade.tpo.marketplace.controllers.users.UserRequest;

import lombok.Data;

@Data
public class CartRequest {
    private Long id; // No hace falta
    private UserRequest buyer; 
    private float totalPrice;
}
