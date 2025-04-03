package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
   
    private String userName;
   
    private String password;
    
    private String name;
   
    private String surname;
  
    private String email;
  
    //private PurchaseFile purchaseFile;
    
}
