package com.uade.tpo.marketplace.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductCatalog { //Un catalogo esta hecho/contiene varios productos, pero no hereda de producto: no es un tipo de producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private List<Product> products;
    @OneToOne(cascade = CascadeType.ALL) //Cada usuario-comprador tiene un solo carrito. Puede tener varias compras, pero un solo carrito a la vez
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


}