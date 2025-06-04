package com.uade.tpo.marketplace.controllers.search;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.SearchBook;
import com.uade.tpo.marketplace.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBookDTO {
  private Long id;
  @NotNull
  private LocalDateTime date;
  @NotNull
  @JsonBackReference
  private User user;
  @NotNull
  private Book book;
 

  public SearchBook toEntity() {
    return SearchBook.builder()
        .id(this.id)
        .date(this.date)
        .book(this.book)
        .user(this.user)
        .build();
  }
}