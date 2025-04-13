package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;

import org.springframework.transaction.annotation.Transactional;

public interface CartService {

    public Cart getCartById(Long cartId) throws Exception;
    @Transactional
    Cart createCart() throws Exception;
    @Transactional
    CartItem addItemBook(Cart cart,Long bookId) throws Exception;
    @Transactional
    CartItem addItemMusicAlbum(Cart cart, Long musicAlbumId) throws Exception;
    
    @Transactional
    public void removeBookFromCart(Cart cart, Long bookId) throws Exception;


    @Transactional
    public void removeItemBookFromCart(Long cartId, Long bookId) throws Exception;

    

    public void emptyCart(Long cartId) throws Exception;
    public Buy checkout(Long cartId) throws Exception;
}