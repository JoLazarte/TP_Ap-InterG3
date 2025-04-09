package com.uade.tpo.marketplace.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductRequest {
    protected Long id;
    protected String title;
    protected String author;
    protected String description;
    protected float price;
    protected int stock;
    protected List<String> urlImage;
   
}
