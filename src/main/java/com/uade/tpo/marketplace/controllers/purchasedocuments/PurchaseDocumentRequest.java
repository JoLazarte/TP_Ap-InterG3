package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.User;

import lombok.Data;  


@Data
public class PurchaseDocumentRequest {
    private Long id;
    private String purchaseId;
    private LocalDate purchaseDate;
    private String buyerName;
    private double totalPrice;
    private String paymentMethod;
    private String description;
    private double totalAmount;
    private User buyer;
    private Buy buy;
}
