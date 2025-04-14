package com.uade.tpo.marketplace.controllers.cartitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.MusicAlbum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
   
    private Book book;  
    
    private MusicAlbum musicAlbum;
    private int quantityBook;
    private int quantityMalbum;
    @JsonIgnore
    @NotNull
    private Cart cart; 

     public CartItem toEntityForBook() {
        return CartItem.builder()
                .id(this.id)
                .book(this.book)
                .quantityBook(this.quantityBook)
                .cart(this.cart)
                .build();
    }

    public CartItem toEntityForMalbum() {
        return CartItem.builder()
                .id(this.id)
                .musicAlbum(this.musicAlbum)
                .quantityMalbum(this.quantityMalbum)
                .cart(this.cart)
                .build();
    }

    public Long getBookId() {
        return this.book.getId();
    }

    public Long getMusicAlbumId() {
        return this.musicAlbum.getId();
    }
    public Long getCartId() {
        return this.cart.getId();
    }
}
