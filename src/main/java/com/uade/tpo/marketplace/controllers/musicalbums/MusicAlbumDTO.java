package com.uade.tpo.marketplace.controllers.musicalbums;

import java.util.List;

import com.uade.tpo.marketplace.entity.Genre;

import lombok.Data;

@Data
public class MusicAlbumDTO {
    private String title;
    private String author;
    private String recordLabel;
    private int year;
    private String description;
    private String isrc; 
    private double price;
    private List<Genre> genres;
    private int stock;
    private List<String> urlImage;
}