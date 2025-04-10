package com.uade.tpo.marketplace.controllers.musicalbums;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.controllers.books.BookRequest;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;
import com.uade.tpo.marketplace.service.MusicAlbumService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("musicAlbums")
public class MusicAlbumController {

    @Autowired
    private MusicAlbumService musicAlbumService;

    @GetMapping
    public ResponseEntity<Page<MusicAlbum>> getMusicAlbums(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if(author != null) {
            if (page == null || size == null)
                return ResponseEntity.ok(musicAlbumService.getMusicAlbumByAuthor(author, PageRequest.of(0, Integer.MAX_VALUE)));
            return ResponseEntity.ok(musicAlbumService.getMusicAlbumByAuthor(author, PageRequest.of(page, size)));
        }
        if (page == null || size == null)
            return ResponseEntity.ok(musicAlbumService.getMusicAlbums(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(musicAlbumService.getMusicAlbums(PageRequest.of(page, size)));
    }

    @GetMapping("/{musicAlbumId}")
    public ResponseEntity<MusicAlbum> getmusicAlbumById(@PathVariable Long musicAlbumId) {
        Optional<MusicAlbum> result = musicAlbumService.getMusicAlbumById(musicAlbumId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{musicAlbumId}")
    public ResponseEntity<MusicAlbum> updatemusicAlbumStock(@PathVariable Long musicAlbumId, @RequestBody MusicAlbumRequest musicAlbumRequest) {
        musicAlbumService.updateStock(musicAlbumId, musicAlbumRequest.getStock());
        return getmusicAlbumById(musicAlbumId);
    }

    @PostMapping
    public ResponseEntity<Object> createmusicAlbum(@RequestBody MusicAlbumRequest musicAlbumRequest)
            throws MusicAlbumDuplicateException {
        MusicAlbum result = musicAlbumService.createMusicAlbum(
                musicAlbumRequest.getTitle(),
                musicAlbumRequest.getAuthor(),
                musicAlbumRequest.getRecordLabel(),
                musicAlbumRequest.getYear(),
                musicAlbumRequest.getDescription(),
                musicAlbumRequest.getIsrc(),
                musicAlbumRequest.getGenres(),
                musicAlbumRequest.getPrice(),
                musicAlbumRequest.getUrlImage());
                
        return ResponseEntity.created(URI.create("/musicAlbums/" + result.getId())).body(result);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MusicAlbum>> createmusicAlbum(@RequestBody List<MusicAlbumRequest> musicAlbumsRequest) throws MusicAlbumDuplicateException {
        List<MusicAlbum> createdMusicAlbums = new ArrayList<>();

        for (MusicAlbumRequest musicAlbumRequest : musicAlbumsRequest) {
            MusicAlbum musicAlbum = musicAlbumService.createMusicAlbum(
                musicAlbumRequest.getTitle(),
                musicAlbumRequest.getAuthor(),
                musicAlbumRequest.getRecordLabel(),
                musicAlbumRequest.getYear(),
                musicAlbumRequest.getDescription(),
                musicAlbumRequest.getIsrc(),
                musicAlbumRequest.getGenres(),
                musicAlbumRequest.getPrice(),
                musicAlbumRequest.getUrlImage());
            createdMusicAlbums.add(musicAlbum);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(createdMusicAlbums);
}
}
