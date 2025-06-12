package com.uade.tpo.marketplace.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.repository.BuyRepository;
import com.uade.tpo.marketplace.service.BuyService;

import jakarta.transaction.Transactional;

@Service
public class BuyServiceImpl implements BuyService{
   
    @Autowired
    private BuyRepository buyRepository;

    @Transactional
    public List<Buy> getUserBuys(Long userId) throws Exception {
        try{
            return buyRepository.findByUserId(userId);
          }catch(Exception error) {
            throw new Exception("[BuyService.getUserBuys] -> " + error.getMessage());
          }
    }

    @Transactional
    public Buy createBuy(Cart cart) throws Exception {
      try{
        Buy buy = Buy.builder()
          .buyDate(LocalDateTime.now())
          .user(cart.getUser())
          .cart(cart)
          .build();

        List<BuyItem> itemsBuyed = cart.generateBuyItems();
        buy.setItems(itemsBuyed);

        return buyRepository.save(buy);

      } catch(Exception error){
        throw new Exception("[BuyService.createBuy] -> " + error.getMessage());
      }
  }

    
}
