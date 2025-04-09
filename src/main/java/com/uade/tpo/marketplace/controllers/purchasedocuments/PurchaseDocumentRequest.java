package com.uade.tpo.marketplace.controllers.purchasedocuments;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.User;

import lombok.Data;  


@Data
public class PurchaseDocumentRequest {
    private Long id;
    private String purchaseId;
    private LocalDate purchaseDate;
    private String buyerName;
    private List<Product> productList;
    private Map<Product, Integer> productQuantities;
    private double totalPrice;
    private String paymentMethod;
    private String description;
    private double totalAmount;
    private User user;
}
