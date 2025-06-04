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
    private String urlImage;

    public MusicAlbumDTO(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
    double price, List<Genre> genres, int stock, String urlImage
                  ) {
                this.id= id;
                this.title = title;
                this.author = author;
                this.recordLabel = recordLabel;
                this.year = year;
                this.description = description;
                this.isrc = isrc;
                this.price = price;
                this.genres = genres;
                this.stock = stock;
                this.urlImage = urlImage;
    }
    
    public MusicAlbum toEntity() {
        return new MusicAlbum(
                this.id,
                this.title,
                this.author,
                this.recordLabel,
                this.year,
                this.description,
                this.isrc,
                this.price,
                this.genres,
                this.stock,
                this.urlImage
                );
    }
}