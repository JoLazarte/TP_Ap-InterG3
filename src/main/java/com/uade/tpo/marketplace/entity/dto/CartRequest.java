package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CartRequest {
    // private Long id; // No hace falta
    // private BuyerRequest buyer; // Lo dejás comentado si todavía no implementaste Buyer/User
    private float totalPrice;
}