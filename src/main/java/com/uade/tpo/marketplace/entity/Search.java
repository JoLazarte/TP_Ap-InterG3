package com.uade.tpo.marketplace.entity;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.search.SearchDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Search {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonBackReference
  private User user;

  @ManyToOne
  @JoinColumn(nullable = false, name = "book_id")
  private Book book;

  @ManyToOne
  @JoinColumn(nullable = false, name = "malbum_id")
  private MusicAlbum malbum;

  public SearchDTO toDTO() {
    return SearchDTO.builder()
        .id(this.id)
        .date(this.date)
        .book(this.book)
        .malbum(this.malbum)
        .user(user)
        .build();
  }
}

