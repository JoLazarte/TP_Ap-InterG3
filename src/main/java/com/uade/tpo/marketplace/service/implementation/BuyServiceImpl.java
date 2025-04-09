package com.uade.tpo.marketplace.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.BuyRepository;
import com.uade.tpo.marketplace.service.BuyService;

import jakarta.transaction.Transactional;

@Service
public class BuyServiceImpl implements BuyService{
   
    @Autowired
    private BuyRepository buyRepository;

    @Override
    public Page<Buy> getBuys(PageRequest pageRequest) {
        return buyRepository.findAll(pageRequest);
    }

    @Override
    public Optional<Buy> getBuyById(Long buyId) {
        return buyRepository.findById(buyId);
    }

    @Override
    @Transactional
    public Buy createBuy(Buy buy) {
        return buyRepository.save(buy);
    }

    @Override
    @Transactional
    public void deleteBuy(Long buyId) {
        buyRepository.deleteById(buyId);
    }

    @Override
    @Transactional
    public List<Buy> getUserBuys(User user) throws Exception {
        try{
            return buyRepository.findByUser(user);
          }catch(Exception error) {
            throw new Exception("[BuyService.getUserBuys] -> " + error.getMessage());
          }
    }


    
}
