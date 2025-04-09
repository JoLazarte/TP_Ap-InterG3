package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ProductCatalog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductCatalogService {
    Page<ProductCatalog> getProducts(PageRequest pageRequest);

    Optional<ProductCatalog> getProductById(Long id);

    ProductCatalog createProduct(Product product);
    List<Product> filterProducts(ProductCatalog productCatalog, String title);
}