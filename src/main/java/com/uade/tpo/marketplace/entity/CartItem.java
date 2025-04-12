package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartItemDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false, name = "musicAlbum_id")
    private MusicAlbum musicAlbum;

    @Column
    private int quantityBook;
    @Column
    private int quantityMalbum;

    public Long getBookId() {
        return this.book.getId();
    }

    public Long getMusicAlbumId() {
        return this.musicAlbum.getId();
    }
    

    public CartItemDTO toDTOForBook() {
        return CartItemDTO.builder()
                .id(this.id)
                .book(this.book)
                .quantityBook(this.quantityBook)
                .cart(this.cart)
                .build();
    }
    public CartItemDTO toDTOForMalbum() {
        return CartItemDTO.builder()
                .id(this.id)
                .musicAlbum(this.musicAlbum)
                .quantityMalbum(this.quantityMalbum)
                .cart(this.cart)
                .build();
    }

    public double calculateTotalBook() {
        return book.getPrice() * quantityBook;
    }
    public double calculateTotalMusicAlbum() {
        return musicAlbum.getPrice() * quantityMalbum;
    }
    public double getSubTotal() {
        return this.calculateTotalBook() + this.calculateTotalMusicAlbum();
    }
   
}