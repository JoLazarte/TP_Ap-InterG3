package com.uade.tpo.marketplace.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Comentado porque aún no existe la clase Usuario
    // @ManyToOne
    // @JoinColumn(name = "usuario_id", nullable = false)
    // private Usuario usuario;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;

    @Column
    private float total;
}