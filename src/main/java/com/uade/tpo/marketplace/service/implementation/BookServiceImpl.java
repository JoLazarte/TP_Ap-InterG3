package com.uade.tpo.marketplace.service.implementation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.exceptions.BookDuplicateException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.repository.SearchBookRepository;
import com.uade.tpo.marketplace.repository.WishListBookRepository;
import com.uade.tpo.marketplace.service.BookService;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
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
  
    // Implementación de métodos para filtrar productos activos
    public Page<Book> getActiveBooks(PageRequest pageable) throws Exception {
        try{
            return bookRepository.findAllActive(pageable);
        }catch (Exception error) {
            throw new Exception("[BookServiceImpl.getActiveBooks] -> " + error.getMessage());
        }
    }
    
    public Page<Book> getActiveBooksByAuthor(String author, PageRequest pageable) {
        return bookRepository.findByAuthorAndActive(author, pageable);
    }
    
    public Book getActiveBookById(Long bookId) throws Exception {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ProductException("producto no encontrado"));
            if (!book.isActive()) {
                throw new ProductException("El producto no está activo");
            }
            return book;
          } catch (Exception error) {
            throw new Exception("[BookServiceImpl.getActiveBookById] -> " + error.getMessage());
          }
    }
    
    // Métodos de administración para activar/desactivar productos
    @Transactional
    public void activateBook(Long bookId) throws Exception {
        try {
            if (!bookRepository.existsById(bookId)) {
                throw new ProductException("El libro con id: '" + bookId + "' no existe.");
            }
            bookRepository.updateActiveStatus(bookId, true);
        } catch (Exception error) {
            throw new Exception("[BookServiceImpl.activateBook] -> " + error.getMessage());
        }
    }
    
    @Transactional
    public void deactivateBook(Long bookId) throws Exception {
        try {
            if (!bookRepository.existsById(bookId)) {
                throw new ProductException("El libro con id: '" + bookId + "' no existe.");
            }
            bookRepository.updateActiveStatus(bookId, false);
        } catch (Exception error) {
            throw new Exception("[BookServiceImpl.deactivateBook] -> " + error.getMessage());
        }
    }
    
    @Transactional
    public void updateActiveStatus(Long bookId, boolean active) throws Exception {
        try {
            if (!bookRepository.existsById(bookId)) {
                throw new ProductException("El libro con id: '" + bookId + "' no existe.");
            }
            bookRepository.updateActiveStatus(bookId, active);
        } catch (Exception error) {
            throw new Exception("[BookServiceImpl.updateActiveStatus] -> " + error.getMessage());
        }
    }
    
    // Métodos para gestión de descuentos
    @Transactional
    public void updateDiscount(Long bookId, BigDecimal discountPercentage, Boolean discountActive) throws Exception {
        try {
            if (!bookRepository.existsById(bookId)) {
                throw new ProductException("El libro con id: '" + bookId + "' no existe.");
            }
            
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new ProductException("Libro no encontrado"));
            
            if (discountPercentage != null) {
                // Validate discount percentage
                if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(90)) > 0) {
                    throw new ProductException("El porcentaje de descuento debe estar entre 0 y 90");
                }
                book.setDiscountPercentage(discountPercentage);
            }
            
            if (discountActive != null) {
                book.setDiscountActive(discountActive);
            }
            
            bookRepository.save(book);
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[BookServiceImpl.updateDiscount] -> " + error.getMessage());
        }
    }

   
}