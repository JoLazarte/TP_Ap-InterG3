package com.uade.tpo.marketplace.controllers.wishlist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListBook;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListBookDTO {
  private Long id;
  @NotNull
  @JsonBackReference
  private User user;
  @NotNull
  private Book book;

  public WishListBook toEntity() {
    return WishListBook.builder()
        .id(this.id)
        .user(user)
        .book(book)
        .build();
  }
}
