package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.WishListItem;
@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
    public List<WishListItem> findAllByUserId(Long userId);
    void deleteByBookId(Long id);
    void deleteByMalbumId(Long id);
}
