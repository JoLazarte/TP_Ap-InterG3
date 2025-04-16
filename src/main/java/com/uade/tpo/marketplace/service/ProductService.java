package com.uade.tpo.marketplace.service;

import java.util.List;


import com.uade.tpo.marketplace.entity.Product;

public interface ProductService {
    public Product getProductById(Long id) throws Exception;
    
    public List<Product> getAllProducts() throws Exception;

    public Product createProduct(Product product) throws Exception;

}
