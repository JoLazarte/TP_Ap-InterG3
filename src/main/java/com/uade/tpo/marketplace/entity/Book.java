package com.uade.tpo.marketplace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
                List<Category> categories, float price, int stock, List<String> urlImage,
                User administrator) {
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.description = description;
        this.isbn = isbn;
        this.categories = categories;
        this.price = price;
        this.stock = stock;
        this.urlImage = urlImage;
        this.administrator = administrator;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;


    @Column
    private String editorial;

    @Column
    private String description;

    @Column
    private String isbn;

    @ManyToMany

    @JoinTable(
    name = "book_category",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
    @Column
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    @Column
    private User administrator; 
    
}
