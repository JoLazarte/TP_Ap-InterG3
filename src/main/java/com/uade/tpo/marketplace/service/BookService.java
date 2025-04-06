package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;

public interface BookService {
    public Page<Book> getBooks(PageRequest pageRequest);

    public Optional<Book> getBookById(Long bookId);

    @Transactional
    public void updateStock(Long bookId, int newStock);

    public Page<Book> getBooksByAuthor(String author, PageRequest pageable);

    public Book createBook(String title, String author, String editorial, String description, String isbn, Float price, List<String> urlImages) throws BookDuplicateException;
}