package com.uade.tpo.marketplace.controllers.search;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.SearchMusicAlbum;
import com.uade.tpo.marketplace.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMusicAlbumDTO {
  private Long id;
  @NotNull
  private LocalDateTime date;
  @NotNull
  @JsonBackReference
  private User user;
  @NotNull
  private MusicAlbum malbum;
 

  public SearchMusicAlbum toEntity() {
    return SearchMusicAlbum.builder()
        .id(this.id)
        .date(this.date)
        .malbum(this.malbum)
        .user(this.user)
        .build();
  }
}