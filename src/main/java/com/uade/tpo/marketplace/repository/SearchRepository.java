package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Search;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    public List<Search> findAllByUserId(Long userId);
    void deleteByBookId(Long id);
    void deleteByMalbumId(Long id);
}
