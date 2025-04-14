package com.uade.tpo.marketplace.controllers.musicalbums;

import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.MusicAlbum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class MusicAlbumDTO extends ProductDTO {
    private String title;//???
    private String author;//????
    private String recordLabel;
    private int year;
    private String description;
    private String isrc; 
    private double price;
    private List<Genre> genres;
    private int stock;
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