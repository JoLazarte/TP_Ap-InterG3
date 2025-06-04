package com.uade.tpo.marketplace.controllers.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.SearchBook;
import com.uade.tpo.marketplace.entity.SearchMusicAlbum;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListBook;
import com.uade.tpo.marketplace.entity.WishListMusicAlbum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String firstName; 
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Role role;
    @JsonManagedReference
    @JsonIgnore
    private Cart cart;
    @JsonIgnore
    private List<Buy> orders;
    @JsonIgnore
    private List<WishListMusicAlbum> wishListMalbums;
    @JsonIgnore
    private List<WishListBook> wishListBooks;
    @JsonIgnore
    private List<SearchBook> lastBookSearches;
    @JsonIgnore
    private List<SearchMusicAlbum> lastMalbumSearches;

    public User toEntity() {
        return new User(
                this.id,
                this.username,
                this.firstName,
                this.lastName,
                this.email,
                this.password,
                this.role,
                this.cart,
                this.orders,
                this.wishListMalbums,
                this.wishListBooks,
                this.lastBookSearches,
                this.lastMalbumSearches
                );     
                
    }
}
