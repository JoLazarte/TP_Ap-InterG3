package com.uade.tpo.marketplace.controllers.cartitems;

import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.service.CartItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<Object> createCartItem(@RequestBody CartItem cartItem) {
        CartItem result = cartItemService.createCartItem(cartItem);
        return ResponseEntity.created(URI.create("/cart-items/" + result.getId())).body(result);
    }
}
