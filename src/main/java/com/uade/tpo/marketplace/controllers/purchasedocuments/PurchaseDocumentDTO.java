package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.User;

//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;  


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDocumentDTO {
    private Long id;
    //@NotNull
    @JsonBackReference
    private Buy buy;
   // @NotNull
    private User user;
   // @NotNull
    private LocalDate purchaseDate;
   // @NotNull
    private double totalPrice;
   // @NotNull
    private String paymentMethod; //podriamos convertirlo en un enum
   // @NotNull
    private String description;

    
}
