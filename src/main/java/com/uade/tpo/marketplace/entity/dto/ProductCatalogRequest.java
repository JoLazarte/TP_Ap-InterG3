package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductCatalogRequest {
    // private Long id; // No hace falta?
    private String title;
    private String author;
    private String description;
    private float price;
    private int stock;
    private List<String> urlImage;
}