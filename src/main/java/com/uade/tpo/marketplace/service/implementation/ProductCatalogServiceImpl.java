package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.ProductCatalog;
import com.uade.tpo.marketplace.repository.ProductCatalogRepository;
import com.uade.tpo.marketplace.service.ProductCatalogService;

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

    
    public Page<ProductCatalog> getProducts(PageRequest pageRequest) {
        return productCatalogRepository.findAll(pageRequest);
    }

   
    public Optional<ProductCatalog> getProductById(Long id) {
        return productCatalogRepository.findById(id);
    }

  
    public Buy createBuy(Cart cart) throws Exception {
    try{
      Buy buy = Buy.builder()
        .buyDate(LocalDateTime.now())
        .user(cart.getUser())
        .build();

      List<BuyItem> buyItems = cart.generateBuyItems();

      buy.setItems(buyItems);

      return buyRepository.save(buy);
    }catch(Exception error){
      throw new Exception("[BuyService.createBuy] -> " + error.getMessage());
    }
  }


    @Transactional
    public List<Product> filterProducts(ProductCatalog productCatalog, String title) {
        return productCatalogRepository.findByTitle(productCatalog, title);
        
    }
}