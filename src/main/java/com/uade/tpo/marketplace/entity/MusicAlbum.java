package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity
@Table(name="PRODUCT")
public class MusicAlbum extends Product{

    public MusicAlbum() {
    }
    
    public MusicAlbum(String title, String author, String recordLabel, int year, String description, String isrc,
                  Genre genres, float price, int stock, List<String> urlImage
                  ) {
    this.title = title;
    this.author = author;
    this.recordLabel = recordLabel;
    this.year = year;
    this.description = description;
    this.isrc = isrc;
    this.genres = genres;
    this.price = price;
    this.stock = stock;
    this.urlImage = urlImage;
}

    @Column
    private String recordLabel;

    @Column
    private int year;

    @Column
    private String isrc;

    @Enumerated(EnumType.STRING)  
    private Genre genres;
    
}
