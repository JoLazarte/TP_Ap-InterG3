package com.uade.tpo.marketplace.controllers.carts;

import com.uade.tpo.marketplace.controllers.cartitems.CartItemDTO;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.uade.tpo.marketplace.exceptions.CartException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.CartService;
import com.uade.tpo.marketplace.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ResponseData<?>> getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
    try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart.toDTO()));
        //return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[CartController.getUserCart] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo recuperar el carro."));
        }
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<ResponseData<?>> addBookToCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long bookId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = authUser.getCart();
            CartItem addedBook = cartService.addItemBook(cart, bookId);
            CartItemDTO addedItemRequest = addedBook.toDTOForBook();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(addedItemRequest));

        } catch (UserException | CartException | ProductException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[CartController.addItemToCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo agregar el item al carro"));
        }
    }
   
    @PutMapping("/musicAlbum/{musicAlbumId}")
    public ResponseEntity<ResponseData<?>> addMusicAlbumToCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long musicAlbumId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = authUser.getCart();
            CartItem addedMalbum = cartService.addItemMusicAlbum(cart, musicAlbumId);  
            CartItemDTO addedItemRequest = addedMalbum.toDTOForMalbum();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(addedItemRequest));

        } catch (UserException | CartException | ProductException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
        System.out.printf("[CartController.addItemToCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo agregar el item al carro"));
        }
    }

    

    
}