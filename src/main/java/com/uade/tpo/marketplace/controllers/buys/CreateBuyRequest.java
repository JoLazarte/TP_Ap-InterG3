package com.uade.tpo.marketplace.controllers.buys;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBuyRequest {
    
    @NotNull
    private List<CreateBuyItemRequest> items;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CreateBuyItemRequest {
    
    @NotNull
    private double finalPrice;
    
    @NotNull
    private int totalQuantity;
    
    // Solo IDs, no objetos completos
    private Long bookId;
    
    private Long musicAlbumId;
} 