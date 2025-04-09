package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data 
@Entity
public class Book extends Product{

    public Book() {
    }
    
    public Book(String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, float price, int stock, List<String> urlImage) {
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

    @ManyToMany
    @JoinTable(
    name = "book_category",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Column
    private List<GenreBook> genreBooks;

    
}
