package com.uade.tpo.marketplace.controllers.books;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.service.BookService;

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
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        if(author != null) {
            if (page == null || size == null)
                return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(0, Integer.MAX_VALUE)));
            return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(page, size)));
        }

        if (page == null || size == null)
            return ResponseEntity.ok(bookService.getBooks(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(bookService.getBooks(PageRequest.of(page, size)));
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseData<?>> createBooks(@RequestBody List<BookDTO> bookDTOs) {
        try{
        List<Book> createdBooks = new ArrayList<>();

        for (BookDTO bookDTO : bookDTOs) {
            Book book = bookService.createBook(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getEditorial(),
                bookDTO.getDescription(),
                bookDTO.getIsbn(),
                bookDTO.getGenreBooks(),
                bookDTO.getPrice(),
                bookDTO.getStock(),
                bookDTO.getUrlImage()
        );
        createdBooks.add(book);
    }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdBooks));
    } catch (Exception error) {
        System.out.printf("[BookController.createBooks] -> %s", error.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear la lista de libros"));
    }
}

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> result = bookService.getById(bookId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBookStock(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) {
        bookService.updateStock(bookId,bookDTO.getStock());
        return getBookById(bookId);
    }

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookDTO bookDTO){
        try {
            Book book = bookService.createBook(
                    bookDTO.getTitle(),
                    bookDTO.getAuthor(),
                    bookDTO.getEditorial(),
                    bookDTO.getDescription(),
                    bookDTO.getIsbn(),
                    bookDTO.getGenreBooks(),
                    bookDTO.getPrice(),
                    bookDTO.getStock(),
                    bookDTO.getUrlImage());
        return ResponseEntity.created(URI.create("/books/" + book.getId())).body(book);
        } catch (Exception error) {
            System.out.printf("[BookController.createBook] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear el libro"));
        }
   
    }
}