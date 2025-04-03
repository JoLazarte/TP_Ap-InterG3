package com.uade.tpo.marketplace.entity;

import jakarta.persistence.Column;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Buyer extends User {
    
    public Buyer(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    @Column
    private String userName;
    @Column
    private String password;
    //@OneToMany(mappedBy = "buyer")
    //private Cart cart; //(relacion con "carrito")

    /* 
    public void buyCart(Cart cart){

    }
    public boolean searchProduct(Product product){
        //Acede al catalo de productos y busca si existe lo que está buscando
        return false;
    }

    */  
}
