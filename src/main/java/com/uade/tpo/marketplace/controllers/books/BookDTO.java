package com.uade.tpo.marketplace.controllers.books;

import java.util.List;
import com.uade.tpo.marketplace.entity.GenreBook;

import lombok.Data;

@Data
public class BookDTO {
    private Long id; //no hace falta que este
    private String title;
    private String author;
    private String editorial;
    private String description;
    private String isbn;
    private List<GenreBook> genreBooks;
    private double price;
    private int stock;
    private List<String> urlImage;
}
