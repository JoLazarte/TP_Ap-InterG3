package com.uade.tpo.marketplace.controllers.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Genre;
import com.uade.tpo.marketplace.entity.GenreBook;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.Role;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.MusicAlbumService;
import com.uade.tpo.marketplace.service.UserService;

@RestController
@RequestMapping("/mockup")
public class MockupController {
  @Autowired
  private UserService userService;

  @Autowired
  private BookService bookService;

  @Autowired
  private MusicAlbumService malbumService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/initializeBooks")
  public ResponseEntity<ResponseData<String>> initializeBooksDB(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      userService.getUserByUsername(userDetails.getUsername());

      Book book1 = new Book(null,"1984", "George Orwell", "Secker & Warburg", "Una distopía política sobre un régimen totalitario.", "978-0451524935", 
                    List.of(GenreBook.FICCION, GenreBook.DYSTOPIA, GenreBook.POLITICAL),18.99, 12, "https://covers.openlibrary.org/b/id/8225631-L.jpg" );
      Book book2 = new Book(null,"Cien Años de soledad", "Gabriel García Marquez", "Editorial Sudamericana", "La saga de la familia Buendía en el mítico pueblo de Macondo.", "978-0307474728", 
                    List.of(GenreBook.MAGICALREALISM, GenreBook.FICCION),21.99, 8, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1327881361i/320.jpg" );
      Book book3 = new Book(null,"Don Quijote de la Mancha","Miguel de Cervantes","Real Academia Española","Las aventuras del ingenioso hidalgo Don Quijote.", "978-8424938437",
                    List.of(GenreBook.CLASSIC, GenreBook.ADVENTURE, GenreBook.SATIRE), 23.99, 10, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1546112331i/3836.jpg" );
      Book book4 = new Book(null, "El Señor de los Anillos", "J.R.R. Tolkien","Minotauro",  "La épica aventura de Frodo para destruir el Anillo Único.", "978-0544003415", 
                    List.of(GenreBook.FANTASY, GenreBook.ADVENTURE), 25.99, 15, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1566425108i/33.jpg" );
      Book book5 = new Book(null,"Rayuela", "Julio Cortázar", "Alfaguara", "Una novela experimental que puede leerse en múltiples órdenes.", "978-8420437484",
                    List.of(GenreBook.ANTINOVEL, GenreBook.FICCION), 19.99, 6,"https://acdn-us.mitiendanube.com/stores/001/029/689/products/rayuela1-70d4301f60dc01384c16171187155984-1024-1024.png "  );
      //Book book6 = new Book(null, );
      //Book book7 = new Book(null, );
      //Book book8 = new Book(null, );
      //Book book9 = new Book(null, );
      //Book book10 = new Book(null, );

      bookService.createBook(book1);
      bookService.createBook(book2);
      bookService.createBook(book3);
      bookService.createBook(book4);
      bookService.createBook(book5);
      //bookService.createBook(book6);
      //bookService.createBook(book7);
      //bookService.createBook(book8);
     // bookService.createBook(book9);
      //bookService.createBook(book10);

      MusicAlbum malbum1 = new MusicAlbum(null,"Abbey Road",  "The Beatles",  "Apple Records", 1969, "Uno de los álbumes más icónicos del rock.", "GBAYE0601690",
               24.99,  List.of(Genre.CLASSICAL, Genre.ROCK),5, "https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg");
      MusicAlbum malbum2 = new MusicAlbum(null,"Dark Side of the Moon", "Pink Floyd","Harvest Records",1973, "Un álbum revolucionario que definió el rock progresivo.", "GBCTA7300014",
               29.99, List.of(Genre.PROGRESSIVE, Genre.PSYCHODELIC), 8, "" );
      MusicAlbum malbum3 = new MusicAlbum(null, "Thriller", "Michael Jackson", "Epic Records", 1982, "El álbum más vendido de todos los tiempos.",  "USSM19902990", 
               27.99, List.of(Genre.POP, Genre.FUNK,Genre.RB), 12, "https://upload.wikimedia.org/wikipedia/en/5/55/Michael_Jackson_-_Thriller.png"  );
      MusicAlbum malbum4 = new MusicAlbum(null, "Back in Black",  "AC/DC", "Atlantic Records",1980, "Un clásico del hard rock que marcó una época.",  "AUATA7900123",
               23.99, List.of(Genre.ROCK, Genre.HARDROCK), 6, "https://upload.wikimedia.org/wikipedia/commons/9/92/ACDC_Back_in_Black.png" );
      MusicAlbum malbum5 = new MusicAlbum(null, "Nevermind", "Nirvana", "DGC Records", 1991, "El álbum que definió el grunge y cambió el rock alternativo.", "USDGC9100123",
               25.99, List.of(Genre.ROCK, Genre.GRUNGE),10, "https://upload.wikimedia.org/wikipedia/en/b/b7/NirvanaNevermindalbumcover.jpg" );

      malbumService.createMusicAlbum(malbum1);
      malbumService.createMusicAlbum(malbum2);
      malbumService.createMusicAlbum(malbum3);
      malbumService.createMusicAlbum(malbum4);  
      malbumService.createMusicAlbum(malbum5);

      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base inicializada correctamente!"));

    } catch (UserException error) {
      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
    } catch (Exception error) {
      System.out.printf("[MockupController.initializeDB] -> %s", error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo inicializar la DB"));
    }
  }
  @PostMapping("/initializeMusicAlbums")
  public ResponseEntity<ResponseData<String>> initializeMalbumsDB(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      userService.getUserByUsername(userDetails.getUsername());
    
      MusicAlbum malbum1 = new MusicAlbum(null,"Abbey Road",  "The Beatles",  "Apple Records", 1969, "Uno de los álbumes más icónicos del rock.", "GBAYE0601690",
               24.99,  List.of(Genre.CLASSICAL, Genre.ROCK),5, "https://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg");
      MusicAlbum malbum2 = new MusicAlbum(null,"Dark Side of the Moon", "Pink Floyd","Harvest Records",1973, "Un álbum revolucionario que definió el rock progresivo.", "GBCTA7300014",
               29.99, List.of(Genre.PROGRESSIVE, Genre.PSYCHODELIC), 8, "" );
      MusicAlbum malbum3 = new MusicAlbum(null, "Thriller", "Michael Jackson", "Epic Records", 1982, "El álbum más vendido de todos los tiempos.",  "USSM19902990", 
               27.99, List.of(Genre.POP, Genre.FUNK,Genre.RB), 12, "https://upload.wikimedia.org/wikipedia/en/5/55/Michael_Jackson_-_Thriller.png"  );
      MusicAlbum malbum4 = new MusicAlbum(null, "Back in Black",  "AC/DC", "Atlantic Records",1980, "Un clásico del hard rock que marcó una época.",  "AUATA7900123",
               23.99, List.of(Genre.ROCK, Genre.HARDROCK), 6, "https://upload.wikimedia.org/wikipedia/commons/9/92/ACDC_Back_in_Black.png" );
      MusicAlbum malbum5 = new MusicAlbum(null, "Nevermind", "Nirvana", "DGC Records", 1991, "El álbum que definió el grunge y cambió el rock alternativo.", "USDGC9100123",
               25.99, List.of(Genre.ROCK, Genre.GRUNGE),10, "https://upload.wikimedia.org/wikipedia/en/b/b7/NirvanaNevermindalbumcover.jpg" );

      malbumService.createMusicAlbum(malbum1);
      malbumService.createMusicAlbum(malbum2);
      malbumService.createMusicAlbum(malbum3);
      malbumService.createMusicAlbum(malbum4);  
      malbumService.createMusicAlbum(malbum5);

      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base inicializada correctamente!"));

    } catch (UserException error) {
      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
    } catch (Exception error) {
      System.out.printf("[MockupController.initializeDB] -> %s", error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo inicializar la DB"));
    }
  }

  @PostMapping("/initializeAdmins")
  public ResponseEntity<ResponseData<String>> initializeAdminsDB(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      userService.getUserByUsername(userDetails.getUsername());

      User admin1 = new User(null, "jolazarte","Joanna","Lazarte","jolazarte@gmail.com","1abcdeft", Role.ADMIN, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
      User admin2 = new User(null, "cami","Cami","Nani","caminani@gmail.com","9iuhjt33", Role.ADMIN, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
      User admin3 = new User(null, "facundo","Facundo","Solá","fsola@gmail.com","fggh678", Role.ADMIN, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
      User admin4 = new User(null, "santiago","Santiago","Gallero","sgallero@gmail.com","0oph644", Role.ADMIN, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>() );
  

      userRepository.save(admin1);
      userRepository.save(admin2);      
      userRepository.save(admin3);
      userRepository.save(admin4); 
  
      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Base de admins inicializada correctamente!"));

    } catch (UserException error) {
      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.error(error.getMessage()));
    } catch (Exception error) {
      System.out.printf("[MockupController.initializeDB] -> %s", error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudo inicializar la DB"));
    }
  }
}

