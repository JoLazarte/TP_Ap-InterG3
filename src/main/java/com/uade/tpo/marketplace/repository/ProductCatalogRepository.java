package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ProductCatalog;

import jakarta.transaction.Transactional;

@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long>{
    @Query(value = "select b from MusicAlbum b where b.isrc = ?1")
    List<Product> findByTitle(ProductCatalog productCatalog, String title);
   
}
