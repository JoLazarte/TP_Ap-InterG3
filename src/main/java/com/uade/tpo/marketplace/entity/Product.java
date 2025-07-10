package com.uade.tpo.marketplace.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotNull
    @Column
    protected String title;
    @NotNull
    @Column
    protected String author;
    @NotNull
    @Column
    protected String description;
    @NotNull
    @Column
    protected double price;
    @NotNull
    @Column
    protected int stock;
    
    //@NotEmpty
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    protected String urlImage;
    
    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    protected boolean active;
    
    @DecimalMin(value = "0.00", message = "El porcentaje de descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "90.00", message = "El porcentaje de descuento debe ser menor o igual a 90")
    @Column(nullable = false, precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    protected BigDecimal discountPercentage = BigDecimal.ZERO;
    
    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    protected boolean discountActive = false;

    // Method to calculate final price with discount
    public double getFinalPrice() {
        if (!discountActive || discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
            return price;
        }
        
        BigDecimal originalPrice = BigDecimal.valueOf(price);
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100)));
        BigDecimal finalPrice = originalPrice.multiply(discountMultiplier);
        
        return finalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.id,
                this.title,
                this.author,
                this.description,
                this.price,
                this.stock,
                this.urlImage,
                this.active,
                this.discountPercentage,
                this.discountActive,
                this.getFinalPrice());
    }

}