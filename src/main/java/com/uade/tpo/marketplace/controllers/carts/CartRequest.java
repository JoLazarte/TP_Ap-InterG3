package com.uade.tpo.marketplace.controllers.carts;

//import com.uade.tpo.marketplace.controllers.users.UserRequest;
import com.uade.tpo.marketplace.entity.User;

import lombok.Data;

@Data
public class CartRequest {
    private Long id; // No hace falta
    private User buyer; 
    private float totalPrice;
}
