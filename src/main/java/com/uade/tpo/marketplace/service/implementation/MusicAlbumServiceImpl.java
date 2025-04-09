package com.uade.tpo.marketplace.service.implementation;

import java.util.Collections;
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
    private MusicAlbumRepository MusicAlbumRepository;

    public Page<MusicAlbum> getMusicAlbums(PageRequest pageable) {
        return MusicAlbumRepository.findAll(pageable);
    }

    public Optional<MusicAlbum> getMusicAlbumById(Long MusicAlbumId) {
        return MusicAlbumRepository.findById(MusicAlbumId);
    }

    public Page<MusicAlbum> getMusicAlbumByAuthor(String author, PageRequest pageable) {
        return MusicAlbumRepository.findByAuthor(author, pageable);
    }

    public void updateStock(Long MusicAlbumId, int newStock) {
        MusicAlbumRepository.updateStock(MusicAlbumId, newStock);
    }



    public MusicAlbum createMusicAlbum(String title, String author, String recordLabel,int year, String description, String isrc, Genre genres, Float price,
        List<String> urlImages) throws MusicAlbumDuplicateException {
        List<MusicAlbum> musicAlbums = MusicAlbumRepository.findByIsrc(isrc);
        if (musicAlbums.isEmpty())
            return MusicAlbumRepository.save(new MusicAlbum(
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

}
