package com.uade.tpo.marketplace.repository;

import com.uade.tpo.marketplace.entity.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "select b from Buy b where b.user = ?1")
    List<Cart> findByCartId(Long cartId);
}
