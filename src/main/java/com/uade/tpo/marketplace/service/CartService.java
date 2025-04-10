package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartById(Long cartId);

    @Transactional
    Cart createCart() throws Exception;

    @Transactional
    void deleteCart(Long cartId);
 
    @Transactional
    CartItem addItemBook(Cart cart,Long bookId) throws Exception;

    @Transactional
    CartItem addItemMusicAlbum(Cart cart, Long musicAlbumId) throws Exception;

    float calculateTotal(Long cartId);

    
}