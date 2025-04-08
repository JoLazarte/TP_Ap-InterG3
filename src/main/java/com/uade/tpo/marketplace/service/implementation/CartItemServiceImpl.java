// CartItemServiceImpl.java
package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.repository.CartItemRepository;
import com.uade.tpo.marketplace.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}