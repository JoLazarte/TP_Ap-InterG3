package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;

public interface MusicAlbumService {
    
    public Page<MusicAlbum> getMusicAlbums(PageRequest pageRequest);

    public Optional<MusicAlbum> getMusicAlbumById(Long MusicAlbumId);

    @Transactional
    public void updateStock(Long MusicAlbumId, int newStock);

    public Page<MusicAlbum> getMusicAlbumByAuthor(String author, PageRequest pageable);

    public MusicAlbum createMusicAlbum(String title, String author, String recordLabel,int year, String description, String isrc, Genre genres, Float price,
    
    List<String> urlImages) throws MusicAlbumDuplicateException;

    public List<MusicAlbum> filterBooks(String title);
}