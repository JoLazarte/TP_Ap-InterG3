package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uade.tpo.marketplace.entity.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query(value = "select b from Buyer b where b.userName = ?1")
    List<Buyer> findByUserName(String userName);
}
