package com.uade.tpo.marketplace.service.implementation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getBooks(PageRequest pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    };

    public Page<Book> getBooksByAuthor(String author, PageRequest pageable) {
        return bookRepository.findByAuthor(author, pageable);
    }

    public void updateStock(Long bookId, int newStock) {
        bookRepository.updateStock(bookId, newStock);
    }

    public Book createBook(String title, String author, String editorial, String description, String isbn, Float price,
        List<String> urlImages) throws BookDuplicateException {
        List<Book> books = bookRepository.findByIsbn(isbn);
        if (books.isEmpty())
            return bookRepository.save(new Book(
                    title,
                    author,
                    editorial, description,
                    isbn,
                    Collections.emptyList(),
                    price,
                    0,
                    urlImages
                )); 
        throw new BookDuplicateException();
    };
}
