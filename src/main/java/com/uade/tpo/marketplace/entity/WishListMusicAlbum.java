package com.uade.tpo.marketplace.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.wishlist.WishListMusicAlbumDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListMusicAlbum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(nullable = false, name = "user_id")
  @JsonBackReference
  private User user;
  @ManyToOne
  @JoinColumn(nullable = false, name = "malbum_id")
  private MusicAlbum malbum;

  public WishListMusicAlbumDTO toDTO() {
    return WishListMusicAlbumDTO.builder()
        .id(this.id)
        .user(this.user)
        .malbum(this.malbum)
        .build();

  }
}
