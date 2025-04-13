package com.uade.tpo.marketplace.controllers.buys;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.entity.User;

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
    private LocalDateTime buyDate;
    @JsonBackReference
    private PurchaseDocument purchaseDocument;
    private List<BuyItem> itemsBuyed;
    @JsonBackReference
    private User user;
   
    private double totalPrice;
 
    public Buy toEntity() {
        return Buy.builder()
            .buyDate(this.buyDate)
            .purchaseDocument(this.purchaseDocument)
            .user(this.user)
            .itemsBuyed(this.itemsBuyed)
            .build();
  }
    
}
