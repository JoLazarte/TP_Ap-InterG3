package com.uade.tpo.marketplace.controllers.books;

import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.GenreBook;

//import jakarta.validation.constraints.NotEmpty;
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
    private List<String> urlImage;

    public BookDTO(Long id, String title, String author, String editorial, String description, String isbn,
                List<GenreBook> genreBooks, double price, int stock, List<String> urlImage) {
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
                this.urlImage
                );
    }
}
