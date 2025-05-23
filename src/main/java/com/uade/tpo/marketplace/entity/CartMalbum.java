package com.uade.tpo.marketplace.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartMalbumDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CartMalbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false, name = "musicAlbum_id")
    private MusicAlbum musicAlbum;

    @Column
    private int quantityMalbum;

    public Long getMusicAlbumId() {
        return this.musicAlbum.getId();
    }
    
    public CartMalbumDTO toDTOForMalbum() {
        return CartMalbumDTO.builder()
                .id(this.id)
                .musicAlbum(this.musicAlbum)
                .quantityMalbum(this.quantityMalbum)
                .cart(this.cart)
                .build();
    }

    public double calculateTotalMusicAlbum() {
        return musicAlbum.getPrice() * quantityMalbum;
    }
    public double getSubTotal() {
        return this.calculateTotalMusicAlbum();
    }
  
    //*************** Convierto los discos en el carrito a items comprados ******************/
    public BuyItem toBuyItemMalbum() {
        return BuyItem.builder()
                .title(this.getMusicAlbum().getTitle())
                .description(this.getMusicAlbum().getDescription())
                .finalPrice(this.getMusicAlbum().getPrice())
                .totalQuantity(this.getQuantityMalbum())
                .images(new ArrayList<>(this.getMusicAlbum().getUrlImage()))
                .build();

    }
}
