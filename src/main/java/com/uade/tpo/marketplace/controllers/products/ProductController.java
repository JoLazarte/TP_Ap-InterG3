package com.uade.tpo.marketplace.controllers.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseData<?>> getAllProducts() {
      try {
        List<Product> allProducts = productService.getAllProducts();
  
        List<ProductDTO> allProductsDTO = allProducts.stream()
                        .map(Product::toDTO)
                        .toList();
  
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(allProductsDTO));
  
      } catch (Exception error) {
        System.out.printf("[ProductController.getAllProducts] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudieron recuperar los productos"));
      }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseData<?>> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDTO productDTO = product.toDTO();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(productDTO));

         } catch (Exception error) {
            System.out.printf("[ProductController.getProductById] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se encontro el producto"));
            }
    }

    
   
}