package com.uade.tpo.marketplace.controllers.cartitems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartMalbum;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartMalbumDTO {
    private Long id;
    private MusicAlbum musicAlbum;
    private int quantityMalbum;
    @JsonIgnore
    @NotNull
    private Cart cart; 

    public CartMalbum toEntityForMalbum() {
        return CartMalbum.builder()
                .id(this.id)
                .musicAlbum(this.musicAlbum)
                .quantityMalbum(this.quantityMalbum)
                .cart(this.cart)
                .build();
    }

    public Long getMusicAlbumId() {
        return this.musicAlbum.getId();
    }
    public Long getCartId() {
        return this.cart.getId();
    }

}
