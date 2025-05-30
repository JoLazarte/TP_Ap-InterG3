package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.marketplace.entity.Search;

public interface SearchRepository extends JpaRepository<Search, Long> {
    public List<Search> findAllByUserId(Long userId);
    void deleteByProductId(Long id);
}
