package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Product;

public interface ProductService {
    public Optional<Product> getProductsById(Long productId);
    Page<Product> getProducts(PageRequest pageRequest);
    @Transactional
    void updateStock(Long id, int newStock);

}
