package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.Product;

public interface ProductService {
    public Optional<Product> getProductsById(Long productId);
    
    Page<Product> getProducts(PageRequest pageRequest);

    Product createProduct(Product product);

}
