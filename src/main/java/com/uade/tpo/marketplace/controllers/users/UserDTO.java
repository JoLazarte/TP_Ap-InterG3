package com.uade.tpo.marketplace.controllers.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.Search;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListItem;

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
    private List<WishListItem> wishList;
    @JsonIgnore
    private List<Search> lastSearches;

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
                this.wishList,
                this.lastSearches);     
                
    }
}
