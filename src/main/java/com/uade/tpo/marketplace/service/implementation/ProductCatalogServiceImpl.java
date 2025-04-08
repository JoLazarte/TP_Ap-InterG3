// ProductCatalogServiceImpl.java
package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.ProductCatalog;
import com.uade.tpo.marketplace.repository.ProductCatalogRepository;
import com.uade.tpo.marketplace.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Override
    public Page<ProductCatalog> getProducts(PageRequest pageRequest) {
        return productCatalogRepository.findAll(pageRequest);
    }

    @Override
    public Optional<ProductCatalog> getProductById(Long id) {
        return productCatalogRepository.findById(id);
    }

    @Override
    public void updateStock(Long id, int newStock) {
        productCatalogRepository.updateStock(id, newStock);
    }

    @Override
    public ProductCatalog createProduct(ProductCatalog product) {
        return productCatalogRepository.save(product);
    }
    @Override
    public List<ProductCatalog> filterProducts(String title) {
        return productCatalogRepository.findByTitleContainingIgnoreCase(title);
    }
}