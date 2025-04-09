package com.uade.tpo.marketplace.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
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

    // Identificador único de la compra, que debe estar asociada a una "compra", asi se obtiene la lista de productos/el carrito comprado con toda la info necesaria
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_id")
    @JsonManagedReference
    private Buy buy;

    // Fecha en la que se realizó la compra
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    // Nombre del comprador que tiene que estar asociado a un usuario
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference
    private User buyer;

    // Precio total de la compra
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    // Método de pago utilizado en la compra
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; //podriamos convertirlo en un enum

    // Descripción adicional de la compra
    @Column
    private String description;

}
