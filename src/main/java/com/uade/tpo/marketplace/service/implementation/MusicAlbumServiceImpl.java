package com.uade.tpo.marketplace.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;
import com.uade.tpo.marketplace.repository.MusicAlbumRepository;

import com.uade.tpo.marketplace.service.MusicAlbumService;

@Service
public class MusicAlbumServiceImpl implements MusicAlbumService {

    @Autowired
    private MusicAlbumRepository musicAlbumRepository;

    public Page<MusicAlbum> getMusicAlbums(PageRequest pageable) {
        return musicAlbumRepository.findAll(pageable);
    }

    public Optional<MusicAlbum> getMusicAlbumById(Long musicAlbumId) {
        return musicAlbumRepository.findById(musicAlbumId);
    }

    public Page<MusicAlbum> getMusicAlbumByAuthor(String author, PageRequest pageable) {
        return musicAlbumRepository.findByAuthor(author, pageable);
    }

    public void updateStock(Long musicAlbumId, int newStock) {
        musicAlbumRepository.updateStock(musicAlbumId, newStock);
    }



    public MusicAlbum createMusicAlbum(String title, String author, String recordLabel,int year, String description, String isrc, List<Genre> genres, Float price,
        List<String> urlImages) throws MusicAlbumDuplicateException {
        List<MusicAlbum> musicAlbums = musicAlbumRepository.findByIsrc(isrc);
        if (musicAlbums.isEmpty())
            return musicAlbumRepository.save(new MusicAlbum(
                    title,
                    author,
                    recordLabel, 
                    year,
                    description,
                    isrc,
                    genres,
                    price,
                    0,
                    urlImages
                    )); // falta agregar administrator
        throw new MusicAlbumDuplicateException();
    }

    public List<MusicAlbum> filterBooks(String title) {
        return musicAlbumRepository.findByTitleContainingIgnoreCase(title);
    }

}
