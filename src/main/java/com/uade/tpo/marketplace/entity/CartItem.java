package com.uade.tpo.marketplace.entity;

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
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "music_album_id")
    private MusicAlbum musicAlbum;

    private int quantity;

    public float calculateTotal() {
        if (book != null) {
            return book.getPrice() * quantity;
        } else if (musicAlbum != null) {
            return musicAlbum.getPrice() * quantity;
        }
        return 0f;
    }
}