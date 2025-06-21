package com.uade.tpo.marketplace.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Buy;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long>  {
    @Query(value = "select b from Buy b where b.buyDate = ?1")
    Optional<Buy> findByDate(LocalDateTime buyDate);

    @Query(value = "select b from Buy b where b.user.id = ?1")
    List<Buy> findByUserId(Long userId);

}
