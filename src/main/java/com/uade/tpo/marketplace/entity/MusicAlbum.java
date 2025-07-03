package com.uade.tpo.marketplace.entity;

import java.util.List;

import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MusicAlbum extends Product{

    public MusicAlbum(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
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
                this.active = true; // Valor por defecto
    }
    
    public MusicAlbum(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
    double price, List<Genre> genres, int stock, String urlImage, boolean active
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
                this.active = active;
    }

    @Column
    private String recordLabel;

    @Column
    private int year;

    @Column
    private String isrc;

    @Enumerated(EnumType.STRING)  
    private List<Genre> genres;

    public MusicAlbumDTO toDTO() {
        return new MusicAlbumDTO(
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
                this.urlImage,
                this.active
                );
    }
    
}
