// CartItemService.java
package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.CartItem;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemService {
    @Transactional
    CartItem createCartItem(CartItem cartItem);
}