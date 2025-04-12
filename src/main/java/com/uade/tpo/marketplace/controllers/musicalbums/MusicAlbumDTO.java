package com.uade.tpo.marketplace.controllers.musicalbums;

import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Genre;

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
}