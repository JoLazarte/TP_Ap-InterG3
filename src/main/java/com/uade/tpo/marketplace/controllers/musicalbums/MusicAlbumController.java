package com.uade.tpo.marketplace.controllers.musicalbums;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.ResponseData;
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
@RequestMapping("/musicAlbums")
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
        Optional<MusicAlbum> result = musicAlbumService.getById(musicAlbumId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{musicAlbumId}")
    public ResponseEntity<MusicAlbum> updatemusicAlbumStock(@PathVariable Long musicAlbumId, @RequestBody MusicAlbumDTO musicAlbumDTO) {
        musicAlbumService.updateStock(musicAlbumId, musicAlbumDTO.getStock());
        return getmusicAlbumById(musicAlbumId);
    }

    @PostMapping
    public ResponseEntity<Object> createmusicAlbum(@RequestBody MusicAlbumDTO musicAlbumDTO){
        try{
            MusicAlbum result = musicAlbumService.createMusicAlbum(
                musicAlbumDTO.getTitle(),
                musicAlbumDTO.getAuthor(),
                musicAlbumDTO.getRecordLabel(),
                musicAlbumDTO.getYear(),
                musicAlbumDTO.getDescription(),
                musicAlbumDTO.getIsrc(),
                musicAlbumDTO.getGenres(),
                musicAlbumDTO.getPrice(),
                musicAlbumDTO.getUrlImage());
                
            return ResponseEntity.created(URI.create("/musicAlbums/" + result.getId())).body(result);
        } catch (Exception error) {
            System.out.printf("[MusicAbumController.createMusicAlbum] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear el disco"));
        }

    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseData<?>> createMusicAlbums(@RequestBody List<MusicAlbumDTO> musicAlbumDTOs){
        try{
        List<MusicAlbum> createdMusicAlbums = new ArrayList<>();

        for (MusicAlbumDTO musicAlbumDTO : musicAlbumDTOs) {
            MusicAlbum musicAlbum = musicAlbumService.createMusicAlbum(
                musicAlbumDTO.getTitle(),
                musicAlbumDTO.getAuthor(),
                musicAlbumDTO.getRecordLabel(),
                musicAlbumDTO.getYear(),
                musicAlbumDTO.getDescription(),
                musicAlbumDTO.getIsrc(),
                musicAlbumDTO.getGenres(),
                musicAlbumDTO.getPrice(),
                musicAlbumDTO.getUrlImage());
            createdMusicAlbums.add(musicAlbum);
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdMusicAlbums));
    } catch (Exception error) {
        System.out.printf("[MusicAlbumController.createMusicAlbums] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear la lista de discos."));
      }
    }
}
