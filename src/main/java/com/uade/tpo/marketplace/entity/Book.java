package com.uade.tpo.marketplace.entity;

import java.util.List;

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
public class Book extends Product{

    
    public Book(String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, List<String> urlImage) {
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
    private List<GenreBook> genreBooks;

    public Book orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }

    
}
