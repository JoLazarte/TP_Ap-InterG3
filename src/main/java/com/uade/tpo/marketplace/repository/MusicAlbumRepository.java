package com.uade.tpo.marketplace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.MusicAlbum;

@Repository
public interface MusicAlbumRepository extends JpaRepository<MusicAlbum, Long> {

    @Query(value = "select b from MusicAlbum b where b.id = ?1")
    Optional<MusicAlbum> findMusicAlbumById(Long MusicAlbumId);

    @Query(value = "select b from MusicAlbum b where b.isrc = ?1")
    List<MusicAlbum> findByIsrc(String isrc);

    @Query(value = "select b from MusicAlbum b where b.author = ?1")
    PageImpl<MusicAlbum> findByAuthor(String author, Pageable page);

    @Modifying
    @Query(value = "update MusicAlbum b set b.stock = ?2 where b.id = ?1")
    void updateStock(Long id, int newStock);
    
}