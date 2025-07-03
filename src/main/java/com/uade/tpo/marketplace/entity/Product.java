package com.uade.tpo.marketplace.entity;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
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

    public ProductDTO toDTO() {
        return new ProductDTO(
                this.id,
                this.title,
                this.author,
                this.description,
                this.price,
                this.stock,
                this.urlImage,
                this.active);
    }

}
