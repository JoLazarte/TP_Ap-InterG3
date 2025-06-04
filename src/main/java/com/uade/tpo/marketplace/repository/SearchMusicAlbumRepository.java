package com.uade.tpo.marketplace.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.SearchMusicAlbum;

@Repository
public interface SearchMusicAlbumRepository extends JpaRepository<SearchMusicAlbum, Long> {
    public List<SearchMusicAlbum> findMalbumsByUserId(Long userId);
    void deleteByMalbumId(Long id);
}
