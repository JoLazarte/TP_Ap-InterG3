package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CategoryRequest {
   private Long id; //no hace falta que este
   private String categoryName;
   //private Product product;
}
