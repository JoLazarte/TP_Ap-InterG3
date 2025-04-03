package com.uade.tpo.marketplace.entity;
import jakarta.persistence.Column;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Admin extends User {
    
    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    @Column
    private String userName;
    @Column
    private String password;
    //@OneToMany(mappedBy = "admin")
    //private Product product; //(relacion con "producto")
    
  /* 
    public void setProduct(Product product){
      //publica el producto, que se creo en el controlador de "Producto", dentro del catalogo de productos
    }
    public void modifyProduct(Product product){
      //modifica el producto dentro del catalogo
    }
    public void deleteProduct(Product product){
      // elimina el producto del catalogo
    }
*/
}
