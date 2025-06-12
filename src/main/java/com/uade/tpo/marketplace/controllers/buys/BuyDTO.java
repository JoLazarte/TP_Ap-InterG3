package com.uade.tpo.marketplace.controllers.buys;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.Cart;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyDTO {

    private Long id;
    @NotNull
    private LocalDateTime buyDate;
    //@NotNull
  
    @NotNull
    private List<BuyItem> items;
    @NotNull
    @JsonBackReference
    private User user;
    @NotNull
    private double totalPrice;
    @JsonBackReference
    private Cart cart;
 
    public Buy toEntity() {
        return Buy.builder()
            //.id(this.id)
            .buyDate(this.buyDate)
            .user(this.user)
            .cart(this.cart)
            .items(this.items)
            .build();
  }
    
}
