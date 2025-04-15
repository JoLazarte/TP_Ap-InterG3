package com.uade.tpo.marketplace.controllers.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;

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
    private String firstName; 
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Role role;
    @JsonManagedReference
    @JsonIgnore
    private Cart cart;
    @JsonIgnore
    private List<Buy> orders;
    @JsonIgnore
    private List<PurchaseDocument> purchaseDocuments;

    public User toEntity() {
        return new User(
                this.id,
                this.firstName,
                this.lastName,
                this.email,
                this.username,
                this.password,
                this.role,
                this.cart,
                this.orders,
                this.purchaseDocuments
                );
    }
}
