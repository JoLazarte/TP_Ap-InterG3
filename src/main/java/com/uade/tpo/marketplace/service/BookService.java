package com.uade.tpo.marketplace.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;

public interface BookService {
    public Page<Book> getBooks(PageRequest pageRequest) throws Exception;
  
    public Book getBookById(Long bookId) throws Exception;

    public Book updateBook(Book book) throws Exception;

    @Transactional
    public void deleteBook(Long bookId) throws Exception;

    @Transactional
    public void updateStock(Long bookId, int newStock);

    public Page<Book> getBooksByAuthor(String author, PageRequest pageable);

    public Book createBook(Book book) throws BookDuplicateException;

    // Métodos para filtrar productos activos
    public Page<Book> getActiveBooks(PageRequest pageRequest) throws Exception;
    
    public Page<Book> getActiveBooksByAuthor(String author, PageRequest pageable);
    
    public Book getActiveBookById(Long bookId) throws Exception;
    
    // Métodos de administración para activar/desactivar productos
    @Transactional
    public void activateBook(Long bookId) throws Exception;
    
    @Transactional
    public void deactivateBook(Long bookId) throws Exception;
    
    @Transactional
    public void updateActiveStatus(Long bookId, boolean active) throws Exception;

    //public List<Book> filterBooks(String title);
}