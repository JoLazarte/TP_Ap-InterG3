package com.uade.tpo.marketplace.controllers.books;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
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
@RequestMapping("books")
public class BooksController {

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
public ResponseEntity<List<Book>> createBooks(@RequestBody List<BookRequest> bookRequests) {
    List<Book> createdBooks = new ArrayList<>();

    for (BookRequest request : bookRequests) {
        Book book = bookService.createBook(
            request.getTitle(),
            request.getAuthor(),
            request.getEditorial(),
            request.getDescription(),
            request.getIsbn(),
            request.getGenreBooks(),
            request.getPrice(),
            request.getStock(),
            request.getUrlImage()
        );
        createdBooks.add(book);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(createdBooks);
}

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> result = bookService.getBookById(bookId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBookStock(@PathVariable Long bookId, @RequestBody BookRequest bookRequest) {
        bookService.updateStock(bookId, bookRequest.getStock());
        return getBookById(bookId);
    }

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookRequest bookRequest)
            throws BookDuplicateException {
        Book result = bookService.createBook(
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getEditorial(),
                bookRequest.getDescription(),
                bookRequest.getIsbn(),
                bookRequest.getGenreBooks(),
                bookRequest.getPrice(),
                bookRequest.getStock(),
                bookRequest.getUrlImage());
        return ResponseEntity.created(URI.create("/books/" + result.getId())).body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> filterProductsByTitle(@RequestParam String title) {
        List<Book> result = bookService.filterBooks(title);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
