package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.Buyer;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;

public interface BuyerService {
    public Page<Buyer> getBuyers(PageRequest pageRequest);

    public Optional<Buyer> getBuyerById(Long userId);

    public Buyer createBuyer(String userName, String password) throws UserDuplicateException;
}
