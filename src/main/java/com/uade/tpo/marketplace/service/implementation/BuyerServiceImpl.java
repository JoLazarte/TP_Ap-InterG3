package com.uade.tpo.marketplace.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Buyer;
import com.uade.tpo.marketplace.exceptions.UserDuplicateException;
import com.uade.tpo.marketplace.repository.BuyerRepository;
import com.uade.tpo.marketplace.service.BuyerService;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    public Page<Buyer> getBuyers(PageRequest pageable) {
        return buyerRepository.findAll(pageable);
    }

    public Optional<Buyer> getBuyerById(Long buyerId) {
        return buyerRepository.findById(buyerId);
    }

    public Buyer createBuyer(String userName, String password) throws UserDuplicateException {
        List<Buyer> buyers = buyerRepository.findByUserName(userName);
        if (buyers.isEmpty())
            return buyerRepository.save(new Buyer(userName,password));
        throw new UserDuplicateException();
    }
    
}
