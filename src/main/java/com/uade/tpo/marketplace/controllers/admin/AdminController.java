package com.uade.tpo.marketplace.controllers.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.MusicAlbumService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MusicAlbumService musicAlbumService;

    // Endpoints para obtener todos los productos (incluyendo inactivos)
    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) throws Exception {

        if(author != null) {
            if (page == null || size == null)
                return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(0, Integer.MAX_VALUE)));
            return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(page, size)));
        }

        if (page == null || size == null)
            return ResponseEntity.ok(bookService.getBooks(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(bookService.getBooks(PageRequest.of(page, size)));
    }

    @GetMapping("/musicAlbums")
    public ResponseEntity<Page<MusicAlbum>> getAllMusicAlbums(
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

    // Endpoints para activar/desactivar productos masivamente
    @PutMapping("/books/{bookId}/toggle-status")
    public ResponseEntity<ResponseData<?>> toggleBookStatus(@PathVariable Long bookId, @RequestBody Map<String, Boolean> status) {
        try {
            Boolean active = status.get("active");
            if (active == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("Se requiere el campo 'active'"));
            }
            bookService.updateActiveStatus(bookId, active);
            String message = active ? "Libro activado correctamente" : "Libro desactivado correctamente";
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(message));
        } catch (Exception error) {
            System.out.printf("[AdminController.toggleBookStatus] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el estado del libro"));
        }
    }

    @PutMapping("/musicAlbums/{musicAlbumId}/toggle-status")
    public ResponseEntity<ResponseData<?>> toggleMusicAlbumStatus(@PathVariable Long musicAlbumId, @RequestBody Map<String, Boolean> status) {
        try {
            Boolean active = status.get("active");
            if (active == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("Se requiere el campo 'active'"));
            }
            musicAlbumService.updateActiveStatus(musicAlbumId, active);
            String message = active ? "Álbum musical activado correctamente" : "Álbum musical desactivado correctamente";
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(message));
        } catch (Exception error) {
            System.out.printf("[AdminController.toggleMusicAlbumStatus] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el estado del álbum musical"));
        }
    }

    // Endpoints para obtener estadísticas
    @GetMapping("/stats")
    public ResponseEntity<ResponseData<?>> getStats() {
        try {
            Page<Book> allBooks = bookService.getBooks(PageRequest.of(0, Integer.MAX_VALUE));
            Page<Book> activeBooks = bookService.getActiveBooks(PageRequest.of(0, Integer.MAX_VALUE));
            Page<MusicAlbum> allMusicAlbums = musicAlbumService.getMusicAlbums(PageRequest.of(0, Integer.MAX_VALUE));
            Page<MusicAlbum> activeMusicAlbums = musicAlbumService.getActiveMusicAlbums(PageRequest.of(0, Integer.MAX_VALUE));
            
            Map<String, Object> stats = Map.of(
                "totalBooks", allBooks.getTotalElements(),
                "activeBooks", activeBooks.getTotalElements(),
                "inactiveBooks", allBooks.getTotalElements() - activeBooks.getTotalElements(),
                "totalMusicAlbums", allMusicAlbums.getTotalElements(),
                "activeMusicAlbums", activeMusicAlbums.getTotalElements(),
                "inactiveMusicAlbums", allMusicAlbums.getTotalElements() - activeMusicAlbums.getTotalElements()
            );
            
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(stats));
        } catch (Exception error) {
            System.out.printf("[AdminController.getStats] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudieron obtener las estadísticas"));
        }
    }
}
