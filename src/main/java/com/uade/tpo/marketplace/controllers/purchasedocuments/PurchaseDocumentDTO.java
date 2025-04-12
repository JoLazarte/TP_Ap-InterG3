package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;  


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDocumentDTO {
    private Long id;
    @JsonManagedReference
    private Buy buy;
    private LocalDate purchaseDate;
    @JsonBackReference
    private User buyer;
    private double totalPrice;
    private String paymentMethod; //podriamos convertirlo en un enum
    private String description;

    
}
