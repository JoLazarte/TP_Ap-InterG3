package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;
import java.util.List;

import com.uade.tpo.marketplace.entity.User;

@Data
public class ProductCatalogRequest {
    private Long id; // No hace falta?
    private String title;
    private String author;
    private String description;
    private float price;
    private int stock;
    private List<String> urlImage;
    private User user;
}
