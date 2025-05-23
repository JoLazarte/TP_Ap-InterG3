package com.uade.tpo.marketplace.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.purchasedocuments.PurchaseDocumentDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class PurchaseDocument {

   // Constructor que inicializa la descripción del documento de compra
    public PurchaseDocumentDTO toDTO() {
        return PurchaseDocumentDTO.builder()
            .id(this.id)
            .buy(buy)
            .purchaseDate(this.purchaseDate)
            .user(user)
            .totalPrice(this.totalPrice)
            .paymentMethod(this.paymentMethod)
            .description(this.description)
            .build();
    }


   // Identificador único de la entidad, generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


   // Identificador único de la compra, que debe estar asociada a una "compra", asi se obtiene la lista de productos/el carrito comprado con toda la info necesaria
    //@NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buy_id")
    @JsonBackReference
    private Buy buy;

   // Fecha en la que se realizó la compra
    //@NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

   // Precio total de la compra
   // @NotNull
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

   // Método de pago utilizado en la compra
    //@NotNull
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; //podriamos convertirlo en un enum

   // Descripción adicional de la compra
    @Column
    private String description;
    //@NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference
    private User user;

}
