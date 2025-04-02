package com.uade.tpo.marketplace.entity;
import lombok.Builder;
import lombok.Data;

@Data //tengo todos los getters y setters
@Builder //nos permite crear obj, extiende esta clase
public class Categories {
    private int id;
    private String description;
    
}
