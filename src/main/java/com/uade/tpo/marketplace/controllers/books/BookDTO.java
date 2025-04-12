package com.uade.tpo.marketplace.controllers.books;

import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.GenreBook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO extends ProductDTO{
   
    private String title;//?????
    private String author;//?????
    private String editorial;
    private String description;
    private String isbn;
    private List<GenreBook> genreBooks;
    private double price;
    private int stock;
    private List<String> urlImage;
}
