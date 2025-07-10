package com.uade.tpo.marketplace.controllers.products;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTO {
    
    @DecimalMin(value = "0.00", message = "El porcentaje de descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "90.00", message = "El porcentaje de descuento debe ser menor o igual a 90")
    private BigDecimal discountPercentage;
    
    private Boolean discountActive;
}
