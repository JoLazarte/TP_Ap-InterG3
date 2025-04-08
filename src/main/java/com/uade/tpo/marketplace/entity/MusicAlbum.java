package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data 
@Entity
public class MusicAlbum extends Product{

    public MusicAlbum() {
    }
    
    public MusicAlbum(String title, String author, String recordLabel, int year, String description, String isrc,
                  List<Genre> genres, float price, int stock, List<String> urlImage,
                  User administrator) {
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
    //this.administrator = administrator;
}

    @Column
    private String recordLabel;

    @Column
    private int year;

    @Column
    private String isrc;

    @Column    
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    @Column
    private User administrator; 
    
}
