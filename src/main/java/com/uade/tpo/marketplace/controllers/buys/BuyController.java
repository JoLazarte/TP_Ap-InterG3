package com.uade.tpo.marketplace.controllers.buys;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.BuyService;
import com.uade.tpo.marketplace.service.UserService;

@RestController
@RequestMapping("/buys")
public class BuyController {

    @Autowired
    private BuyService buyService;
    @Autowired
    private UserService userService;

    @GetMapping("")
  public ResponseEntity<ResponseData<?>> getUserBuys(@AuthenticationPrincipal UserDetails userDetails) {
    try {
        User authUser = userService.getUserByUsername(userDetails.getUsername());

        List<Buy> buys = buyService.getUserBuys(authUser.getId()).stream().toList();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(buys));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[BuyController.getUserBuys] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseData.error("No se pudieron obtener las compras."));
        }
    }

    @GetMapping("/{buyId}")
    public ResponseEntity<Buy> getBuyById(@PathVariable Long buyId) {
        Optional<Buy> result = buyService.getBuyById(buyId);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @PostMapping
    public ResponseEntity<Object> createBuy(@RequestBody Buy buy) {
        Buy result = buyService.createBuy(buy);
        return ResponseEntity.created(URI.create("/buys/" + result.getId())).body(result);
    }

    @DeleteMapping("/{buyId}")
    public ResponseEntity<Void> deleteBuy(@PathVariable Long buyId) {
        buyService.deleteBuy(buyId);
        return ResponseEntity.noContent().build();
    }
    
}
