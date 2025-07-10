package com.uade.tpo.marketplace.entity;

import java.math.BigDecimal;
import java.util.List;

import com.uade.tpo.marketplace.controllers.books.BookDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column
    private String editorial;
    @NotNull
    @Column
    private String isbn;
    @NotNull
    @Enumerated(EnumType.STRING)
    private List<GenreBook> genreBooks;

    public Book(Long id, String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, String urlImage) {
        this.id=id;            
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.description = description;
        this.isbn = isbn;
        this.genreBooks = genreBooks;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
        this.active = true; // Valor por defecto
        this.discountPercentage = BigDecimal.ZERO;
        this.discountActive = false;
    }
    
    public Book(Long id, String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, String urlImage, boolean active) {
        this.id=id;            
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.description = description;
        this.isbn = isbn;
        this.genreBooks = genreBooks;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
        this.active = active;
        this.discountPercentage = BigDecimal.ZERO;
        this.discountActive = false;
    }
    
    public Book(Long id, String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, String urlImage, boolean active,
                BigDecimal discountPercentage, boolean discountActive) {
        this.id=id;            
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.description = description;
        this.isbn = isbn;
        this.genreBooks = genreBooks;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
        this.active = active;
        this.discountPercentage = discountPercentage != null ? discountPercentage : BigDecimal.ZERO;
        this.discountActive = discountActive;
    }

    public BookDTO toDTO() {
        return new BookDTO(
                this.id,
                this.title,
                this.author,
                this.editorial,
                this.description,
                this.isbn,
                this.genreBooks,
                this.price,
                this.stock,
                this.urlImage,
                this.active,
                this.discountPercentage,
                this.discountActive,
                this.getFinalPrice()
                );
    }
    
}