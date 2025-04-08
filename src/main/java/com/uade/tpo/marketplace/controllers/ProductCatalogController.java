package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.ProductCatalog;
import com.uade.tpo.marketplace.entity.dto.ProductCatalogRequest;
import com.uade.tpo.marketplace.service.ProductCatalogService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogService productCatalogService;

    @GetMapping
    public ResponseEntity<Page<ProductCatalog>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if (page == null || size == null)
            return ResponseEntity.ok(productCatalogService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));

        return ResponseEntity.ok(productCatalogService.getProducts(PageRequest.of(page, size)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductCatalog> getProductById(@PathVariable Long productId) {
        Optional<ProductCatalog> result = productCatalogService.getProductById(productId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductCatalog> updateProductStock(
            @PathVariable Long productId,
            @RequestBody ProductCatalogRequest request) {
        productCatalogService.updateStock(productId, request.getStock());
        return getProductById(productId);
    }
    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductCatalogRequest request) {
        ProductCatalog product = new ProductCatalog();
        product.setTitle(request.getTitle());
        product.setAuthor(request.getAuthor());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setUrlImage(request.getUrlImage());
    
        ProductCatalog result = productCatalogService.createProduct(product);
        return ResponseEntity.created(URI.create("/products/" + result.getId())).body(result);
    }
    @GetMapping("/search")
public ResponseEntity<List<ProductCatalog>> filterProductsByTitle(@RequestParam String title) {
    List<ProductCatalog> result = productCatalogService.filterProducts(title);
    if (result.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(result);
}
}