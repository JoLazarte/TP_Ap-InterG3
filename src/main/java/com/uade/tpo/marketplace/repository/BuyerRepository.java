package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query(value = "select b from Buyer b where b.userName = ?1")
    List<Buyer> findByUserName(String userName);
}
