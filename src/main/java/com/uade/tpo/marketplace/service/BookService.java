package com.uade.tpo.marketplace.service;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.Book;
//import com.uade.tpo.marketplace.entity.GenreBook;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;

public interface BookService {
    public Page<Book> getBooks(PageRequest pageRequest) throws Exception;
  
    public Book getBookById(Long bookId) throws Exception;

    @Transactional
    public void updateStock(Long bookId, int newStock);

    public Page<Book> getBooksByAuthor(String author, PageRequest pageable);

    public Book createBook(Book book) throws BookDuplicateException;

    //public List<Book> filterBooks(String title);
}