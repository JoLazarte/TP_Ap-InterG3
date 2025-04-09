package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ProductCatalog;
import com.uade.tpo.marketplace.repository.ProductCatalogRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.ProductCatalogService;
import com.uade.tpo.marketplace.service.ProductService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductService productService;

    public Page<ProductCatalog> getCatalogs(PageRequest pageRequest) {
        return productCatalogRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public ProductCatalog addProduct(Long productCatalogId, Product product) {
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(productCatalogId);
        if (optionalProductCatalog.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }
        ProductCatalog productCatalog = optionalProductCatalog.get();

        // Asignar el producto al catalogo
        product.setProductCatalog(productCatalog);

        // Guardar el producto
        productService.createProduct(product);

        // Agregar el producto al catalogo
        productCatalog.getProducts().add(product);

        return productCatalogRepository.save(productCatalog);
    }

    

}