package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uade.tpo.marketplace.entity.WishListItem;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
    public List<WishListItem> findAllByUserId(Long userId);
    void deleteByProductId(Long id);
}
