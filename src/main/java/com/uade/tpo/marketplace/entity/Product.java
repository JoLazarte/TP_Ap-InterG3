package com.uade.tpo.marketplace.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Product {
    public Product() {
    }
    @jakarta.persistence.Id
    private Long id;
    private String name;

}
