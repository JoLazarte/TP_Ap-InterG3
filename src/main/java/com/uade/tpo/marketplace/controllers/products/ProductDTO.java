package com.uade.tpo.marketplace.controllers.products;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    protected Long id;
    protected String title;
    protected String author;
    protected String description;
    protected double price;
    protected int stock;
    protected List<String> urlImage;
   
    
}
