package com.uade.tpo.marketplace.controllers.products;

//import com.uade.tpo.marketplace.entity.Product;

//import jakarta.validation.constraints.NotEmpty;
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
    
    
}
