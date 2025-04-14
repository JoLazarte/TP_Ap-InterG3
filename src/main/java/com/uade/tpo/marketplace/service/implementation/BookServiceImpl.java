package com.uade.tpo.marketplace.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Book;
//import com.uade.tpo.marketplace.entity.GenreBook;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.service.BookService;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getBooks(PageRequest pageable) throws Exception {
        try{
            return bookRepository.findAll(pageable);
        }catch (Exception error) {
            throw new Exception("[BookServiceImpl.getBooks] -> " + error.getMessage());
        }
    }
    
    public Book getBookById(Long bookId) throws Exception {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ProductException("producto no encontrado"));
            return book;
          } catch (Exception error) {
            throw new Exception("[ProductService.getProductById] -> " + error.getMessage());
          }
    }
    
    public Page<Book> getBooksByAuthor(String author, PageRequest pageable) {
        return bookRepository.findByAuthor(author, pageable);
    }

    public void updateStock(Long bookId, int newStock) {
        bookRepository.updateStock(bookId, newStock);
    }

    public Book createBook(Book book) throws BookDuplicateException {
        List<Book> books = bookRepository.findByIsbn(book.getIsbn());
        if (books.isEmpty())
            return bookRepository.save(book); 
        throw new BookDuplicateException();
    }
  

   
}
