package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;

import com.uade.tpo.marketplace.entity.Buy;

import lombok.Data;  


@Data
public class PurchaseDocumentRequest {
    private Buy buy;
    private LocalDate purchaseDate;
   //private User buyer;
    private double totalPrice;
    private String paymentMethod; //podriamos convertirlo en un enum
    private String description;

    
}
