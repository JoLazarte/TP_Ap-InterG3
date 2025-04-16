package com.uade.tpo.marketplace.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
  

    public Product getProductById(Long id) throws Exception {
          try {
            Product product = productRepository.findById(id).orElseThrow(() -> new ProductException("producto no encontrado"));
            return product;
          } catch (Exception error) {
            throw new Exception("[ProductService.getProductById] -> " + error.getMessage());
          }
    }
    public Product updateProduct(Product product) throws Exception {
          try {
            if (!productRepository.existsById(product.getId())) 
              throw new ProductException("El producto con id: '" + product.getId() + "' no existe.");
            
            Product updatedProduct = productRepository.save(product);
            return updatedProduct;
          } catch (ProductException error) {
            throw new ProductException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[ProductService.updateProduct] -> " + error.getMessage());
          }
    }

    public List<Product> getAllProducts() throws Exception {
        try {
          List<Product> products = productRepository.findAll();
          return products;
        } catch (Exception error) {
          throw new Exception("[ProductService.getAllProducts] -> " + error.getMessage());
        }
    }

    public Product createProduct(Product product) throws Exception {
        try {
          Product createdProduct = productRepository.save(product);
          return createdProduct;
        } catch (Exception error) {
          throw new Exception("[ProductService.createProduct] -> " + error.getMessage());
        }
    }
    
}
