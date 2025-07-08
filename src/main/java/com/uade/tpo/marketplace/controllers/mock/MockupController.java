package com.uade.tpo.marketplace.controllers.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.MusicAlbumService;
import com.uade.tpo.marketplace.service.UserService;

@RestController
@RequestMapping("/mockup")
public class MockupController {

    @Autowired private UserService userService;
    @Autowired private BookService bookService;
    @Autowired private MusicAlbumService malbumService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/initializeAll")
    public ResponseEntity<ResponseData<String>> initializeDatabase(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            userService.getUserByUsername(userDetails.getUsername());

            // Libros
            List<Book> books = List.of(
                new Book(null, "1984", "George Orwell", "Secker & Warburg", "Una distopía política sobre un régimen totalitario.",
                        "978-0451524935", List.of(GenreBook.FICCION, GenreBook.DYSTOPIA, GenreBook.POLITICAL),
                        18.99, 12, "https://covers.openlibrary.org/b/id/8225631-L.jpg", true),
                new Book(null, "Cien Años de soledad", "Gabriel García Marquez", "Editorial Sudamericana",
                        "La saga de la familia Buendía en el mítico pueblo de Macondo.", "978-0307474728",
                        List.of(GenreBook.MAGICALREALISM, GenreBook.FICCION),
                        21.99, 8, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327881361i/320.jpg", true),
                new Book(null, "Don Quijote de la Mancha", "Miguel de Cervantes", "Real Academia Española",
                        "Las aventuras del ingenioso hidalgo Don Quijote.", "978-8424938437",
                        List.of(GenreBook.CLASSIC, GenreBook.ADVENTURE, GenreBook.SATIRE),
                        23.99, 10, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546112331i/3836.jpg", true),
                new Book(null, "El Señor de los Anillos", "J.R.R. Tolkien", "Minotauro",
                        "La épica aventura de Frodo para destruir el Anillo Único.", "978-0544003415",
                        List.of(GenreBook.FANTASY, GenreBook.ADVENTURE),
                        25.99, 15, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1566425108i/33.jpg", true),
                new Book(null, "Rayuela", "Julio Cortázar", "Alfaguara",
                        "Una novela experimental que puede leerse en múltiples órdenes.", "978-8420437484",
                        List.of(GenreBook.ANTINOVEL, GenreBook.FICCION),
                        19.99, 6, "https://acdn-us.mitiendanube.com/stores/001/029/689/products/rayuela1-70d4301f60dc01384c16171187155984-1024-1024.png", true)
            );
            books.forEach(book -> {
                try {
                    bookService.createBook(book);
                } catch (BookDuplicateException e) {
                    // Ignore duplicate books during initialization
                }
            });

            // Álbumes musicales
            // Álbumes musicales
            List<MusicAlbum> albums = List.of(
                new MusicAlbum(null, "Abbey Road", "The Beatles", "Apple Records", 1969,
                        "Uno de los álbumes más icónicos del rock.", "GBAYE0601690",
                        24.99, List.of(Genre.CLASSICAL, Genre.ROCK), 5,
                        "https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg", true),
                new MusicAlbum(null, "Dark Side of the Moon", "Pink Floyd", "Harvest Records", 1973,
                        "Un álbum revolucionario que definió el rock progresivo.", "GBCTA7300014",
                        29.99, List.of(Genre.PROGRESSIVE, Genre.PSYCHODELIC), 8, "", true),
                new MusicAlbum(null, "Thriller", "Michael Jackson", "Epic Records", 1982,
                        "El álbum más vendido de todos los tiempos.", "USSM19902990",
                        27.99, List.of(Genre.POP, Genre.FUNK, Genre.RB), 12,
                        "https://upload.wikimedia.org/wikipedia/en/5/55/Michael_Jackson_-_Thriller.png", true),
                new MusicAlbum(null, "Back in Black", "AC/DC", "Atlantic Records", 1980,
                        "Un clásico del hard rock que marcó una época.", "AUATA7900123",
                        23.99, List.of(Genre.ROCK, Genre.HARDROCK), 6,
                        "https://upload.wikimedia.org/wikipedia/commons/9/92/ACDC_Back_in_Black.png", true),
                new MusicAlbum(null, "Nevermind", "Nirvana", "DGC Records", 1991,
                        "El álbum que definió el grunge y cambió el rock alternativo.", "USDGC9100123",
                        25.99, List.of(Genre.ROCK, Genre.GRUNGE), 10,
                        "https://upload.wikimedia.org/wikipedia/en/b/b7/NirvanaNevermindalbumcover.jpg", true)
            );

            albums.forEach(album -> {
                try {
                    malbumService.createMusicAlbum(album);
                } catch (Exception e) {
                    // Ignore duplicate albums during initialization
                }
            });


            return ResponseEntity.ok(ResponseData.success("Base inicializada correctamente!"));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.println("[MockupController.initializeAll] -> " + error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo inicializar la DB"));
        }
    }
}
