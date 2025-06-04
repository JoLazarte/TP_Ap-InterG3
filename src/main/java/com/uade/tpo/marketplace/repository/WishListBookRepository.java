package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.WishListBook;

@Repository
public interface WishListBookRepository extends JpaRepository<WishListBook, Long> {
    public List<WishListBook> findAllByUserId(Long userId);
    void deleteByBookId(Long id);
}
