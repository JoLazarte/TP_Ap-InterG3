package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
    @ElementCollection
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    protected List<String> urlImage;
    
}
