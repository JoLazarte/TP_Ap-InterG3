package com.uade.tpo.marketplace.controllers.carts;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Optional<Cart> result = cartService.getCartById(cartId);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCart(@RequestBody Cart cart) {
        Cart result = cartService.createCart(cart);
        return ResponseEntity.created(URI.create("/carts/" + result.getId())).body(result);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
 
    @PostMapping("/{cartId}/books")
    public ResponseEntity<Cart> addItemBookToCart(
            @PathVariable Long cartId,
            @RequestBody CartItem cartItem) {

        Cart updatedCart = cartService.addItemBook(cartId, cartItem);
        return ResponseEntity.ok(updatedCart);
    }
    @PostMapping("/{cartId}/musicAlbums")
    public ResponseEntity<Cart> addItemMusicAlbumToCart(
            @PathVariable Long cartId,
            @RequestBody CartItem cartItem) {

        Cart updatedCart = cartService.addItemMusicAlbum(cartId, cartItem);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Float> getCartTotal(@PathVariable Long cartId) {
        float total = cartService.calculateTotal(cartId);
        return ResponseEntity.ok(total);
    }
    
}