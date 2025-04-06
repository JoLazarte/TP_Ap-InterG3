package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data 
@MappedSuperclass
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected String title;

    @Column
    protected String author;

    @Column
    protected String description;

    @Column
    protected float price;

    @Column
    protected int stock;

    @Column
    protected List<String> urlImage;

        // @ManyToOne
        // @JoinColumn(name = "administrador_id", nullable = false)
        // @Column
        // private Administrator administrator; 
    
}
