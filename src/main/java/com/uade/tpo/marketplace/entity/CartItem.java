package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false, name = "musicAlbum_id")
    private MusicAlbum musicAlbum;

    @Column
    private int quantity;

    public Long getBookId() {
        return this.book.getId();
    }

    public Long getMusicAlbumId() {
        return this.musicAlbum.getId();
    }
    public Long getCartId() {
        return this.cart.getId();
    }

    
    

    public float calculateTotalBook() {
        return book.getPrice() * quantity;
    }
    public float calculateTotalMusicAlbum() {
        return musicAlbum.getPrice() * quantity;
    }

   
}