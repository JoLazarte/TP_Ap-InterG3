package com.uade.tpo.marketplace.controllers.books;

import java.math.BigDecimal;
import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.GenreBook;

//import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor

public class BookDTO extends ProductDTO{
    @NotNull
    private String title;//?????
    @NotNull
    private String author;//?????
    @NotNull
    private String editorial;
    @NotNull
    private String description;
    @NotNull
    private String isbn;
    @NotNull
    private List<GenreBook> genreBooks;
    @NotNull
    private double price;
    @NotNull
    private int stock;
    //@NotEmpty
    private String urlImage;
    @NotNull
    private boolean active;
    
    @DecimalMin(value = "0.00", message = "El porcentaje de descuento debe ser mayor o igual a 0")
    @DecimalMax(value = "90.00", message = "El porcentaje de descuento debe ser menor o igual a 90")
    private BigDecimal discountPercentage = BigDecimal.ZERO;
    
    @NotNull
    private boolean discountActive = false;
    
    private double finalPrice;

    public BookDTO(Long id, String title, String author, String editorial, String description, String isbn,
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
        this.finalPrice = price;
    }
    
    public BookDTO(Long id, String title, String author, String editorial, String description, String isbn,
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
        this.finalPrice = price;
    }
    
    public BookDTO(Long id, String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, String urlImage, boolean active,
                BigDecimal discountPercentage, boolean discountActive, double finalPrice) {
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
        this.finalPrice = finalPrice;
    }
    
    public Book toEntity() {
        return new Book(
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
                this.discountActive
                );
    }
}
