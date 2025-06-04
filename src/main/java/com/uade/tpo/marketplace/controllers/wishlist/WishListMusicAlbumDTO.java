package com.uade.tpo.marketplace.controllers.wishlist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListMusicAlbum;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WishListMusicAlbumDTO {
  private Long id;
  @NotNull
  @JsonBackReference
  private User user;
  @NotNull
  private MusicAlbum malbum;

  public WishListMusicAlbum toEntity() {
    return WishListMusicAlbum.builder()
        .id(this.id)
        .user(user)
        .malbum(malbum)
        .build();
  }
}
