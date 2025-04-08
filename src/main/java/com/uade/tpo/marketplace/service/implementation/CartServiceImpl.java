// CartServiceImpl.java
package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.ProductCatalog;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.ProductCatalogRepository;
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
    private ProductCatalogRepository productCatalogRepository;

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
    public Cart addItem(Long cartId, CartItem cartItem) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        Cart cart = optionalCart.get();
        ProductCatalog product = cartItem.getProduct();

        // Verificar stock suficiente
        if (product.getStock() < cartItem.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + product.getTitle());
        }

        // Asignar el cart al cartItem
        cartItem.setCart(cart);

        // Guardar CartItem
        cartItemService.createCartItem(cartItem);

        // Actualizar stock
        product.setStock(product.getStock() - cartItem.getQuantity());
        productCatalogRepository.save(product);

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
                total += item.calculateTotal();
            }
        }
        return total;
    }
}