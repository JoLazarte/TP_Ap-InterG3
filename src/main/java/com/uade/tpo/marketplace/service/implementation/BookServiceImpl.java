package com.uade.tpo.marketplace.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.SearchBookRepository;
import com.uade.tpo.marketplace.repository.WishListBookRepository;
import com.uade.tpo.marketplace.service.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishListBookRepository wishListBookRepository;
    @Autowired
    private SearchBookRepository searchBookRepository;

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
            throw new Exception("[BookServiceImpl.getBookById] -> " + error.getMessage());
          }
    }
    
    public Book createBook(Book book) throws BookDuplicateException {
        List<Book> books = bookRepository.findByIsbn(book.getIsbn());
        if (books.isEmpty())
            return bookRepository.save(book); 
        throw new BookDuplicateException();
    }

    public Book updateBook(Book book) throws Exception {
          try {
            if (!bookRepository.existsById(book.getId())) 
              throw new ProductException("El libro con id: '" + book.getId() + "' no existe.");
            
            Book updatedBook = bookRepository.save(book);
            return updatedBook;
          } catch (ProductException error) {
            throw new ProductException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[BookService.updateBook] -> " + error.getMessage());
          }
    }

    @Transactional
    public void deleteBook(Long bookId) throws Exception {
        try {

            List<Cart> carts = cartRepository.findAll();
              for (Cart cart : carts) {
                  cart.getBookItems().removeIf(item -> item.getBook().getId().equals(bookId));
              }
              cartRepository.saveAll(carts);

              searchBookRepository.deleteByBookId(bookId);
              wishListBookRepository.deleteByBookId(bookId);
              bookRepository.deleteById(bookId);

          } catch (Exception error) {
            throw new Exception("[BookService.deleteBook] -> " + error.getMessage());
          }
    }

    public Page<Book> getBooksByAuthor(String author, PageRequest pageable) {
        return bookRepository.findByAuthor(author, pageable);
    }

    public void updateStock(Long bookId, int newStock) {
        bookRepository.updateStock(bookId, newStock);
    }
  

   
}
