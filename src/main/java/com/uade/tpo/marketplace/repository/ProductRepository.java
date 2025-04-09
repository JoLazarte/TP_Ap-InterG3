package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Product;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Modifying
    @Transactional
    @Query("update Product p set p.stock = ?2 where p.id = ?1")
    void updateStock(Long id, int stock);
}
