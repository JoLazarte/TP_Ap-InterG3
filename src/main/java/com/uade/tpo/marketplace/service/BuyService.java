package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;

public interface BuyService {

    List<Buy> getUserBuys(Long userId) throws Exception;

    public Buy createBuy(Cart cart) throws Exception;
        

}
