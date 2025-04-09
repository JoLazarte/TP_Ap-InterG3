package com.uade.tpo.marketplace.service.implementation;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Optional<Product> getProductsById(Long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsById'");
    }

    @Override
    public Page<Product> getProducts(PageRequest pageRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProducts'");
    }

    @Override
    public void updateStock(Long id, int newStock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStock'");
    }

    @Override
    public Product createProduct(Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creProduct'");
    }
    
}
