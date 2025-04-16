package com.uade.tpo.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;

public interface MusicAlbumService {
    
    public Page<MusicAlbum> getMusicAlbums(PageRequest pageRequest);

    public MusicAlbum getMusicAlbumById(Long MusicAlbumId) throws Exception;

    public MusicAlbum updateMalbum(MusicAlbum musicAlbum) throws Exception;

    @Transactional
    public void deleteMalbum(Long malbumId) throws Exception;

    @Transactional
    public void updateStock(Long MusicAlbumId, int newStock);

    public Page<MusicAlbum> getMusicAlbumByAuthor(String author, PageRequest pageable);

    public MusicAlbum createMusicAlbum(MusicAlbum musicAlbum) throws MusicAlbumDuplicateException;

   // public List<MusicAlbum> getMusicAlbumByTitle(String title);
}