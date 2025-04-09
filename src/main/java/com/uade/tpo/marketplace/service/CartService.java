package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartById(Long cartId);

    @Transactional
    Cart createCart(Cart cart);

    @Transactional
    void deleteCart(Long cartId);
 
    @Transactional
    Cart addItemBook(Long cartId, CartItem cartItem);

    @Transactional
    Cart addItemMusicAlbum(Long cartId, CartItem cartItem);

    float calculateTotal(Long cartId);

    
}