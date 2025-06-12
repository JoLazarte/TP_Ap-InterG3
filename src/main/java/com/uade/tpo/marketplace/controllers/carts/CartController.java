package com.uade.tpo.marketplace.controllers.carts;

import com.uade.tpo.marketplace.controllers.cartitems.CartBookDTO;
import com.uade.tpo.marketplace.controllers.cartitems.CartMalbumDTO;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartBook;
import com.uade.tpo.marketplace.entity.CartMalbum;
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

    @GetMapping("/userCart")
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

    @PutMapping("/update")
    public ResponseEntity<ResponseData<?>> updateCart(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody UpdateCartDTO updateCartDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = authUser.getCart();
            CartBook addedBook;
            
            if (updateCartDTO.getQuantity() != null && updateCartDTO.getQuantity() > 0) {
                addedBook = cartService.addItemBookWithQuantity(cart, updateCartDTO.getBookId(), updateCartDTO.getQuantity());
            } else {
                addedBook = cartService.addItemBook(cart, updateCartDTO.getBookId());
            }
            
            CartBookDTO addedBookDTO = addedBook.toDTOForBook();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(addedBookDTO));
        } catch (UserException | CartException | ProductException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[CartController.updateCart] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo actualizar el carrito"));
        }
    }

    @PutMapping("/update/musicAlbum")
    public ResponseEntity<ResponseData<?>> updateCartMusicAlbum(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody UpdateCartMalbumDTO updateCartMalbumDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = authUser.getCart();
            CartMalbum addedMalbum;
            
            if (updateCartMalbumDTO.getQuantity() != null && updateCartMalbumDTO.getQuantity() > 0) {
                addedMalbum = cartService.addItemMusicAlbumWithQuantity(cart, updateCartMalbumDTO.getMusicAlbumId(), updateCartMalbumDTO.getQuantity());
            } else {
                addedMalbum = cartService.addItemMusicAlbum(cart, updateCartMalbumDTO.getMusicAlbumId());
            }
            
            CartMalbumDTO addedMalbumDTO = addedMalbum.toDTOForMalbum();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(addedMalbumDTO));
        } catch (UserException | CartException | ProductException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[CartController.updateCartMusicAlbum] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo actualizar el carrito"));
        }
    }

    @PutMapping("/musicAlbum/{musicAlbumId}")
    public ResponseEntity<ResponseData<?>> addMusicAlbumToCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long musicAlbumId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = authUser.getCart();
            CartMalbum addedMalbum = cartService.addItemMusicAlbum(cart, musicAlbumId);  
            CartMalbumDTO addedMalbumDTO = addedMalbum.toDTOForMalbum();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(addedMalbumDTO));

        } catch (UserException | CartException | ProductException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[CartController.addMusicAlbumToCart] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo agregar el item al carro"));
        }
    }

    @PutMapping("/book/{bookId}/remove")
    public ResponseEntity<ResponseData<?>> removeBookFromCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long bookId) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        cartService.removeBookFromCart(cart, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart.toDTO()));
      
      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
      } catch (Exception error) {
        System.out.printf("[CartController.removeBookFromCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo quitar el libro del carro."));
      }
    }

    @PutMapping("/musicAlbum/{musicAlbumId}/remove")
    public ResponseEntity<ResponseData<?>> removeMalbumFromCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long musicAlbumId) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        cartService.removeMalbumFromCart(cart, musicAlbumId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart.toDTO()));
      
      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
      } catch (Exception error) {
        System.out.printf("[CartController.removeMalbumFromCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo quitar el disco del carro."));
      }
    }

    @PutMapping("/cartItem/{bookId}/remove")
    public ResponseEntity<ResponseData<?>> removeItemBookFromCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long bookId) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        cartService.removeItemBookFromCart(cart.getId(), bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart.toDTO()));

      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
      } catch (Exception error) {
        System.out.printf("[CartController.removeItemBookFromCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo quitar el item del carro."));
      }
    }

    @PutMapping("/cartItem/{musicAlbumId}/remove")
    public ResponseEntity<ResponseData<?>> removeItemMalbumFromCart(@AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long musicAlbumId) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        cartService.removeItemMalbumFromCart(cart.getId(), musicAlbumId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(cart.toDTO()));

      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
      } catch (Exception error) {
        System.out.printf("[CartController.removeItemMalbumFromCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo quitar el item del carro."));
      }
    }

    @PutMapping("/empty")
    public ResponseEntity<ResponseData<?>> emptyCart(@AuthenticationPrincipal UserDetails userDetails) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
        cartService.emptyCart(cart.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Carrito vaciado correctamente!"));
      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

      } catch (Exception error) {
        System.out.printf("[CartController.emptyCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo vaciar el carro"));
      }
  }

    @PutMapping("/confirm")
    public ResponseEntity<ResponseData<?>> confirmCart(@AuthenticationPrincipal UserDetails userDetails) {
      try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());
        Cart cart = authUser.getCart();
       Buy buy = cartService.checkout(cart.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(buy));

      } catch (UserException | CartException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

      } catch (Exception error) {
        System.out.printf("[CartController.confirmCart] -> %s", error.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo generar la compra"));
      }
    }

    

    
}