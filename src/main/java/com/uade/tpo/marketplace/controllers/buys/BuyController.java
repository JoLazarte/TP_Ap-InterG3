package com.uade.tpo.marketplace.controllers.buys;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.service.BuyService;

@RestController
@RequestMapping("buys")
public class BuyController {

    @Autowired
    private BuyService buyService;

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
