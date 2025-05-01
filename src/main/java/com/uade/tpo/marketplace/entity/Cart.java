package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartBookDTO;
import com.uade.tpo.marketplace.controllers.cartitems.CartMalbumDTO;
import com.uade.tpo.marketplace.controllers.carts.CartDTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "user" })
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartBook> bookItems;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartMalbum> malbumItems;
    
    public CartDTO toDTO() {
        // Crear un nuevo objeto CartRequest
        CartDTO cartRequest = new CartDTO();

        // Asignar los valores básicos
        cartRequest.setId(this.getId());
        cartRequest.setUser(this.getUser());

        // Convertir la lista de cartItems de Cart a una lista de CartItemRequests
        List<CartBookDTO> cartItemRequestsBooks = this.getBookItems().stream()
                .map(CartBook::toDTOForBook)
                .toList();
        List<CartMalbumDTO> cartItemRequestsMalbums = this.getMalbumItems().stream()
                .map(CartMalbum::toDTOForMalbum)
                .toList();
        // Asignar la lista de ItemDTO al CartDTO
        cartRequest.setBookItems(cartItemRequestsBooks);
        cartRequest.setMalbumItems(cartItemRequestsMalbums);
        cartRequest.setTotalPrice(this.calculateTotalPrice());

        return cartRequest; // Devolver el CartDTO convertido
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (CartBook itemBook : this.getBookItems()) {
            total += itemBook.getSubTotal();
        }
        for (CartMalbum itemMalbum : this.getMalbumItems()) {
            total += itemMalbum.getSubTotal();
        }

        return total;
    }
 ////******************************* Compramos los items del carrito ********************************/////
    
    public List<BuyItem> generateBuyItems() {
        List<BuyItem> itemsBuyed = new ArrayList<>();
        this.getBookItems().forEach(item -> {
            // Los libros en el carrito:
            if (item.getQuantityBook() > item.getBook().getStock()) {
                throw new RuntimeException("Not enough stock of product: " + item.getBook().getTitle());
            }
            else {
                item.getBook().setStock(item.getBook().getStock() - item.getQuantityBook());
            }
            BuyItem bookBuyed = item.toBuyItemBook();
            itemsBuyed.add(bookBuyed);
            
        });

        this.getMalbumItems().forEach(item -> {
            //Los discos en el carrito:
            if (item.getQuantityMalbum() > item.getMusicAlbum().getStock()) {
                throw new RuntimeException("Not enough stock of product: " + item.getMusicAlbum().getTitle());
            }
            else {
                item.getMusicAlbum().setStock(item.getMusicAlbum().getStock() - item.getQuantityMalbum());
            }
            BuyItem malbumBuyed = item.toBuyItemMalbum();
            itemsBuyed.add(malbumBuyed);
        });

        return itemsBuyed;
    }
        
}
