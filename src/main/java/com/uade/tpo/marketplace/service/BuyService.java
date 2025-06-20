package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.User;

public interface BuyService {

    List<Buy> getUserBuys(Long userId) throws Exception;
    
    public Buy createBuy(User user, List<BuyItem> items, boolean confirmed) throws Exception;
    
    public void emptyBuy(Long buyId) throws Exception;
    
    public Buy confirmBuy(Long buyId) throws Exception;
        

}
