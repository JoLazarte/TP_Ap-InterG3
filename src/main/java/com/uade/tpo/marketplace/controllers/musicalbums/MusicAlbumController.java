package com.uade.tpo.marketplace.controllers.musicalbums;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.service.MusicAlbumService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<ResponseData<?>> getMusicAlbumById(@PathVariable Long musicAlbumId) {
        try {
            MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(musicAlbumId);
            MusicAlbumDTO musicAlbumDTO = musicAlbum.toDTO();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(musicAlbumDTO));

        } catch (Exception error) {
        System.out.printf("[MusicAlbumController.getMusicAlbumById] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se encontro el producto"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createmusicAlbum(@RequestBody MusicAlbumDTO musicAlbumDTO){
        try{
            MusicAlbum musicAlbum = musicAlbumDTO.toEntity();
            MusicAlbum malbumCreated = musicAlbumService.createMusicAlbum(musicAlbum);
            
            return ResponseEntity.created(URI.create("/books/" + malbumCreated.getId())).body(malbumCreated);
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
            MusicAlbum musicAlbum = musicAlbumDTO.toEntity();
            MusicAlbum musicAlbumCreated = musicAlbumService.createMusicAlbum(musicAlbum);
            createdMusicAlbums.add(musicAlbumCreated);
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdMusicAlbums));
    } catch (Exception error) {
        System.out.printf("[MusicAlbumController.createMusicAlbums] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear la lista de discos."));
      }
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateMalbum(@RequestBody MusicAlbumDTO musicAlbumDTO) {
        try {
            MusicAlbum musicAlbum = musicAlbumDTO.toEntity();
            MusicAlbum updatedMalbum = musicAlbumService.updateMalbum(musicAlbum);
            MusicAlbumDTO updatedMalbumDTO = updatedMalbum.toDTO();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedMalbumDTO));

        }catch (ProductException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[MusicAlbumController.updateMalbum] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el libro"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteMalbum(@PathVariable Long malbumId) {
        try {
            musicAlbumService.deleteMalbum(malbumId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        } catch (Exception error) {
            System.out.printf("[MusicAlbumController.deleteMalbum] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo eliminar el producto"));
        }
    }

    @PutMapping("/{musicAlbumId}")
    public ResponseEntity<ResponseData<?>> updatemusicAlbumStock(@PathVariable Long musicAlbumId, @RequestBody MusicAlbumDTO musicAlbumDTO) {
        musicAlbumService.updateStock(musicAlbumId, musicAlbumDTO.getStock());
        return getMusicAlbumById(musicAlbumId);
    }

}