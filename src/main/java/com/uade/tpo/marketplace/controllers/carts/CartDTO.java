package com.uade.tpo.marketplace.controllers.carts;

import java.util.List;

import com.uade.tpo.marketplace.controllers.cartitems.CartItemDTO;
import com.uade.tpo.marketplace.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private User user;
    private List<CartItemDTO> items;
    private double totalPrice;
}
