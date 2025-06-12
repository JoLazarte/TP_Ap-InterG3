package com.uade.tpo.marketplace.controllers.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartMalbumDTO {
    private Long musicAlbumId;
    private Integer quantity;

    public Long getMusicAlbumId() {
        return musicAlbumId;
    }

    public void setMusicAlbumId(Long musicAlbumId) {
        this.musicAlbumId = musicAlbumId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 