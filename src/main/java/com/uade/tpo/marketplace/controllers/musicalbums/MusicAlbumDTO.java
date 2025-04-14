package com.uade.tpo.marketplace.controllers.musicalbums;

import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.MusicAlbum;

import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class MusicAlbumDTO extends ProductDTO {
    @NotNull
    private String title;//???
    @NotNull
    private String author;//????
    @NotNull
    private String recordLabel;
    @NotNull
    private int year;
    @NotNull
    private String description;
    @NotNull
    private String isrc; 
    @NotNull
    private double price;
    @NotNull
    private List<Genre> genres;
    @NotNull
    private int stock;
    //@NotEmpty
    private List<String> urlImage;

    public MusicAlbum toEntity() {
        return new MusicAlbum(

                this.title,
                this.author,
                this.recordLabel,
                this.year,
                this.description,
                this.isrc,
                this.genres,
                this.price,
                this.stock,
                this.urlImage
                );
    }
}