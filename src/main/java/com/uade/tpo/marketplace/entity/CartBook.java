package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartBookDTO;
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
public class CartBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false, name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;

    @Column
    private int quantityBook;


    public Long getBookId() {
        return this.book.getId();
    }

    public CartBookDTO toDTOForBook() {
        return CartBookDTO.builder()
                .id(this.id)
                .book(this.book)
                //.musicAlbum(this.musicAlbum)
                .quantityBook(this.quantityBook)
                //.quantityMalbum(this.quantityMalbum)
                .cart(this.cart)
                .build();
    }

    public double calculateTotalBook() {
        return book.getPrice() * quantityBook;
    }

    public double getSubTotal() {
        return this.calculateTotalBook();
    }
   //*************** Convierto los libros en el carrito a items comprados ******************/
    public BuyItem toBuyItemBook() {
        return BuyItem.builder()
                .finalPrice(this.getBook().getPrice())
                .totalQuantity(this.getQuantityBook())
                .book(this.getBook())
                .build();

    }

}
