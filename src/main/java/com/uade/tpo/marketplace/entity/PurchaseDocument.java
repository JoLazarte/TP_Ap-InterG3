package com.uade.tpo.marketplace.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;

import lombok.Data;

@Entity
@Data

public class PurchaseDocument {
    public PurchaseDocument() {
    }

    // Constructor que inicializa la descripción del documento de compra
    public PurchaseDocument(String description) {
        this.description = description;
    }

    // Identificador único de la entidad, generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador único de la compra
    @Column(name = "purchase_id", nullable = false, unique = true)
    private String purchaseId;

    // Fecha en la que se realizó la compra
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    // Nombre del comprador
    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    // Lista de productos asociados a la compra
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private List<Product> productList;

    // Mapa que asocia productos con sus cantidades compradas
    @ElementCollection
    @CollectionTable(name = "product_quantities", joinColumns = @JoinColumn(name = "purchase_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantities;

    // Precio total de la compra
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    // Método de pago utilizado en la compra
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    // Descripción adicional de la compra
    @Column
    private String description;

    // Monto total calculado (puede ser redundante si se usa totalPrice)
    private double totalAmount;

    //@OneToOne(mappedBy = "category")
    //private Product product;
}
