package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;

<<<<<<< HEAD
import com.uade.tpo.marketplace.controllers.buys.BuyRequest;
=======
>>>>>>> ca95f6c12ace50ce06e9aa4ac8c81d5567636510
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
<<<<<<< HEAD
    
=======
>>>>>>> ca95f6c12ace50ce06e9aa4ac8c81d5567636510
}
