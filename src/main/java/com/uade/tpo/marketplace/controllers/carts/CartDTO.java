package com.uade.tpo.marketplace.controllers.carts;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartBookDTO;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.controllers.cartitems.CartMalbumDTO;
import com.uade.tpo.marketplace.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    //@NotNull
    @JsonBackReference
    private User user;
    @NotNull
    private List<CartBookDTO> bookItems;
    @NotNull
    private List<CartMalbumDTO> malbumItems;
    @NotNull
    private double totalPrice;
}
