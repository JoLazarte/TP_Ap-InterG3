package com.uade.tpo.marketplace.controllers.products;

import java.math.BigDecimal;

//import com.uade.tpo.marketplace.entity.Product;

//import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    protected Long id;
    @NotNull
    protected String title;
    @NotNull
    protected String author;
    @NotNull
    protected String description;
    @NotNull
    protected double price;
    @NotNull
    protected int stock;
    //@NotEmpty
    protected String urlImage;
    @NotNull
    protected boolean active;
    
    @DecimalMin(value = "0.00", message = "El porcentaje de descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "90.00", message = "El porcentaje de descuento debe ser menor o igual a 90")
    protected BigDecimal discountPercentage = BigDecimal.ZERO;
    
    @NotNull
    protected boolean discountActive = false;
    
    protected double finalPrice;
    
}
