package com.uade.tpo.marketplace.controllers.buys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.CartException;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.BuyService;
import com.uade.tpo.marketplace.service.CartService;
import com.uade.tpo.marketplace.service.UserService;

@RestController
@RequestMapping("/buys")
public class BuyController {

    @Autowired
    private BuyService buyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/userBuys")
    public ResponseEntity<ResponseData<?>> getUserBuys(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            List<BuyDTO> buys = buyService.getUserBuys(authUser.getId()).stream().map(Buy::toDTO).toList();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(buys));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[BuyController.getUserBuys] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudieron obtener las compras."));
        }
    }

    @PutMapping("/process/{cartId}")
    public ResponseEntity<ResponseData<?>> processCartPurchase(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long cartId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Cart cart = cartService.getCartById(cartId);
            
            // Verificar que el carrito pertenece al usuario autenticado
            if (!cart.getUser().getId().equals(authUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseData.error("No tienes permiso para procesar este carrito"));
            }

            Buy buy = cartService.checkout(cartId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(buy.toDTO()));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));
        } catch (CartException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[BuyController.processCartPurchase] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo procesar la compra"));
        }
    }
}
