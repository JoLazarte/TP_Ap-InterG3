package com.uade.tpo.marketplace.entity.dto;

import java.util.List;

import com.uade.tpo.marketplace.entity.Genre;

import lombok.Data;

@Data
public class MusicAlbumRequest {
    private String title;
    private String author;
    private String recordLabel;
    private int year;
    private String description;
    private String isrc; 
    private float price;
    private Genre genres;
    private int stock;
    private List<String> urlImage;
}