package com.uade.tpo.marketplace.controllers.cartitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartBook;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartBookDTO {
    private Long id;
    private Book book;  
    private int quantityBook;
    @JsonIgnore
    @NotNull
    private Cart cart; 

    public CartBook toEntityForBook() {
        return CartBook.builder()
                .id(this.id)
                .book(this.book)
                .quantityBook(this.quantityBook)
                .cart(this.cart)
                .build();
    }

    public Long getBookId() {
        return this.book.getId();
    }

    public Long getCartId() {
        return this.cart.getId();
    }
}
