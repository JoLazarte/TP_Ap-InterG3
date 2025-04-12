package com.uade.tpo.marketplace.controllers.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
   
    private String username;
    
    private String password;
  
    private String firstName;
   
    private String lastName;
    
    private String email;

    private Role role;

    @JsonManagedReference
    private Cart cart;
    
    @JsonManagedReference
    private List<Buy> orders;

    @JsonManagedReference
    private PurchaseDocument purchaseDocument; 
}
