package com.uade.tpo.marketplace.service;

import java.math.BigDecimal;

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

    // Métodos para filtrar productos activos
    public Page<MusicAlbum> getActiveMusicAlbums(PageRequest pageRequest);
    
    public Page<MusicAlbum> getActiveMusicAlbumsByAuthor(String author, PageRequest pageable);
    
    public MusicAlbum getActiveMusicAlbumById(Long musicAlbumId) throws Exception;
    
    // Métodos de administración para activar/desactivar productos
    @Transactional
    public void activateMusicAlbum(Long musicAlbumId) throws Exception;
    
    @Transactional
    public void deactivateMusicAlbum(Long musicAlbumId) throws Exception;
    
    @Transactional
    public void updateActiveStatus(Long musicAlbumId, boolean active) throws Exception;

    // Métodos para gestión de descuentos
    @Transactional
    public void updateDiscount(Long musicAlbumId, BigDecimal discountPercentage, Boolean discountActive) throws Exception;

   // public List<MusicAlbum> getMusicAlbumByTitle(String title);
}