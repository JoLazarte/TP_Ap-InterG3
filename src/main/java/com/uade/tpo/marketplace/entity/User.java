package com.uade.tpo.marketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {
    public User(){
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    //@OneToMany(mappedBy = "user")
    //private PurchaseFile purchaseFile; //(Aca se estableceria la relación con "documento de compra")

    public void registerUser(){

    }
    public void loginUser(){
        
    }
}
