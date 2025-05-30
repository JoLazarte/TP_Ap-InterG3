package com.uade.tpo.marketplace.controllers.search;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;

import com.uade.tpo.marketplace.entity.Search;
import com.uade.tpo.marketplace.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDTO {
  private Long id;
  @NotNull
  private LocalDateTime date;
  @NotNull
  @JsonBackReference
  private User user;
  @NotNull
  private Book book;
  @NotNull
  private MusicAlbum malbum;

  public Search toEntity() {
    return Search.builder()
        .id(this.id)
        .date(this.date)
        .book(this.book)
        .malbum(this.malbum)
        .user(this.user)
        .build();
  }
}