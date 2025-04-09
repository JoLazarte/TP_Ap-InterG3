package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.User;


public interface BuyService {
    public Page<Buy> getBuys(PageRequest pageRequest);
    public Optional<Buy> getBuyById(Long buyId);

    @Transactional
    Buy createBuy(Buy buy);

    @Transactional
    void deleteBuy(Long buyId);

    List<Buy> getUserBuys(User user) throws Exception;
        

}
