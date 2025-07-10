package com.uade.tpo.marketplace.controllers.musicalbums;

import java.math.BigDecimal;
import java.util.List;

import com.uade.tpo.marketplace.controllers.products.ProductDTO;
import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.MusicAlbum;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor

public class MusicAlbumDTO extends ProductDTO {
    @NotNull
    private String title;//???
    @NotNull
    private String author;//????
    @NotNull
    private String recordLabel;
    @NotNull
    private int year;
    @NotNull
    private String description;
    @NotNull
    private String isrc; 
    @NotNull
    private double price;
    @NotNull
    private List<Genre> genres;
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

    public MusicAlbumDTO(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
    double price, List<Genre> genres, int stock, String urlImage
                  ) {
                this.id= id;
                this.title = title;
                this.author = author;
                this.recordLabel = recordLabel;
                this.year = year;
                this.description = description;
                this.isrc = isrc;
                this.price = price;
                this.genres = genres;
                this.stock = stock;
                this.urlImage = urlImage;
                this.active = true; // Valor por defecto
                this.discountPercentage = BigDecimal.ZERO;
                this.discountActive = false;
                this.finalPrice = price;
    }
    
    public MusicAlbumDTO(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
    double price, List<Genre> genres, int stock, String urlImage, boolean active
                  ) {
                this.id= id;
                this.title = title;
                this.author = author;
                this.recordLabel = recordLabel;
                this.year = year;
                this.description = description;
                this.isrc = isrc;
                this.price = price;
                this.genres = genres;
                this.stock = stock;
                this.urlImage = urlImage;
                this.active = active;
                this.discountPercentage = BigDecimal.ZERO;
                this.discountActive = false;
                this.finalPrice = price;
    }
    
    public MusicAlbumDTO(Long id, String title, String author, String recordLabel, int year, String description, String isrc,
    double price, List<Genre> genres, int stock, String urlImage, boolean active,
    BigDecimal discountPercentage, boolean discountActive, double finalPrice
                  ) {
                this.id= id;
                this.title = title;
                this.author = author;
                this.recordLabel = recordLabel;
                this.year = year;
                this.description = description;
                this.isrc = isrc;
                this.price = price;
                this.genres = genres;
                this.stock = stock;
                this.urlImage = urlImage;
                this.active = active;
                this.discountPercentage = discountPercentage != null ? discountPercentage : BigDecimal.ZERO;
                this.discountActive = discountActive;
                this.finalPrice = finalPrice;
    }
    
    public MusicAlbum toEntity() {
        return new MusicAlbum(
                this.id,
                this.title,
                this.author,
                this.recordLabel,
                this.year,
                this.description,
                this.isrc,
                this.price,
                this.genres,
                this.stock,
                this.urlImage,
                this.active,
                this.discountPercentage,
                this.discountActive
                );
    }
}