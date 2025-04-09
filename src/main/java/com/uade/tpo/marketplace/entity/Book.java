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
public class Book extends Product{

    public Book() {
    }
    
    public Book(String title, String author, String editorial, String description, String isbn,
                GenreBook genreBooks, float price, int stock, List<String> urlImage) {
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.description = description;
        this.isbn = isbn;
        this.genreBooks = genreBooks;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
    }

    @Column
    private String editorial;

    @Column
    private String isbn;

    @Enumerated(EnumType.STRING)
    private GenreBook genreBooks;

    
}
