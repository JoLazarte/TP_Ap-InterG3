package com.uade.tpo.marketplace.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;

public interface BuyService {

    @Transactional
    void deleteBuy(Long buyId);

    List<Buy> getUserBuys(Long userId) throws Exception;

    public Buy createBuy(Cart cart) throws Exception;
        

}
