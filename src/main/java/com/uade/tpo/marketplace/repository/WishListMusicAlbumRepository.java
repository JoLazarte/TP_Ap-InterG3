package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.WishListMusicAlbum;

@Repository
public interface WishListMusicAlbumRepository extends JpaRepository<WishListMusicAlbum, Long> {
    public List<WishListMusicAlbum> findAllByUserId(Long userId);
    void deleteByMalbumId(Long id);
}