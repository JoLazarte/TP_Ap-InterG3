package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.CartItemService;
import com.uade.tpo.marketplace.service.CartService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Cart createCart(Cart cart) {
        // Asegurar que la lista de items no sea null
        if (cart.getItems() == null) {
            cart.setItems(new java.util.ArrayList<>());
        }
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
 
    @Override
    @Transactional
    public Cart addItemBook(Long cartId, CartItem cartItem) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = optionalCart.get();
        Book book = cartItem.getBook();



        // Verificar stock suficiente
        if (book.getStock() < cartItem.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + book.getTitle());
        }

        // Asignar el cart al cartItem
        cartItem.setCart(cart);

        // Guardar CartItem
        cartItemService.createCartItem(cartItem);

        // Actualizar stock
        book.setStock(book.getStock() - cartItem.getQuantity());
        productRepository.save(book);

        // Agregar item al carrito
        cart.getItems().add(cartItem);

        // Recalcular total
        cart.setTotal(calculateCartTotal(cart));

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart addItemMusicAlbum(Long cartId, CartItem cartItem) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = optionalCart.get();
        MusicAlbum musicAlbum = cartItem.getMusicAlbum();



        // Verificar stock suficiente
        if (musicAlbum.getStock() < cartItem.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + musicAlbum.getTitle());
        }

        // Asignar el cart al cartItem
        cartItem.setCart(cart);

        // Guardar CartItem
        cartItemService.createCartItem(cartItem);

        // Actualizar stock
        musicAlbum.setStock(musicAlbum.getStock() - cartItem.getQuantity());
        productRepository.save(musicAlbum);

        // Agregar item al carrito
        cart.getItems().add(cartItem);

        // Recalcular total
        cart.setTotal(calculateCartTotal(cart));

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public float calculateTotal(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            return calculateCartTotal(cart);
        }
        return 0f;
    }

    private float calculateCartTotal(Cart cart) {
        float total = 0f;
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                total += item.calculateTotalBook() + item.calculateTotalMusicAlbum();
            }
        }
        return total;
    }
    
}