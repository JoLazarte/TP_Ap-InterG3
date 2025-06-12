package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartBook;
import com.uade.tpo.marketplace.entity.CartMalbum;

import org.springframework.transaction.annotation.Transactional;

public interface CartService {

    public Cart getCartById(Long cartId) throws Exception;
    @Transactional
    Cart createCart() throws Exception;
    @Transactional
    CartBook addItemBook(Cart cart,Long bookId) throws Exception;
    @Transactional
    CartMalbum addItemMusicAlbum(Cart cart, Long musicAlbumId) throws Exception;
    @Transactional
    public void removeBookFromCart(Cart cart, Long bookId) throws Exception;
    @Transactional
    public void removeMalbumFromCart(Cart cart, Long malbumId) throws Exception;
    @Transactional
    public void removeItemBookFromCart(Long cartId, Long bookId) throws Exception;
    @Transactional
    public void removeItemMalbumFromCart(Long cartId, Long malbumId) throws Exception;
    public void emptyCart(Long cartId) throws Exception;
    public Buy checkout(Long cartId) throws Exception;
    @Transactional
    CartBook addItemBookWithQuantity(Cart cart, Long bookId, int quantity) throws Exception;
    @Transactional
    CartMalbum addItemMusicAlbumWithQuantity(Cart cart, Long musicAlbumId, int quantity) throws Exception;

}