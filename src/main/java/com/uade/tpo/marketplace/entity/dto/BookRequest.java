package com.uade.tpo.marketplace.entity.dto;

import java.util.List;
import lombok.Data;

@Data
public class BookRequest {
   private Long id; //no hace falta que este
    private String title;
    private String author;
    private String editorial;
    private String description;
    private String isbn;
   //  private List<Category> categories;
    private float price;
    private int stock;
    private List<String> urlImage;
}
