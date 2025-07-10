package com.uade.tpo.marketplace.controllers.books;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.service.BookService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "true") Boolean activeOnly) throws Exception {

        if(author != null) {
            if (page == null || size == null) {
                if (activeOnly) {
                    return ResponseEntity.ok(bookService.getActiveBooksByAuthor(author, PageRequest.of(0, Integer.MAX_VALUE)));
                } else {
                    return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(0, Integer.MAX_VALUE)));
                }
            }
            if (activeOnly) {
                return ResponseEntity.ok(bookService.getActiveBooksByAuthor(author, PageRequest.of(page, size)));
            } else {
                return ResponseEntity.ok(bookService.getBooksByAuthor(author, PageRequest.of(page, size)));
            }
        }

        if (page == null || size == null) {
            if (activeOnly) {
                return ResponseEntity.ok(bookService.getActiveBooks(PageRequest.of(0, Integer.MAX_VALUE)));
            } else {
                return ResponseEntity.ok(bookService.getBooks(PageRequest.of(0, Integer.MAX_VALUE)));
            }
        }
        
        if (activeOnly) {
            return ResponseEntity.ok(bookService.getActiveBooks(PageRequest.of(page, size)));
        } else {
            return ResponseEntity.ok(bookService.getBooks(PageRequest.of(page, size)));
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseData<?>> createBooks(@RequestBody List<BookDTO> bookDTOs) {
        try{
        List<Book> createdBooks = new ArrayList<>();

        for (BookDTO bookDTO : bookDTOs) {
            Book book = bookDTO.toEntity();
            Book bookCreated = bookService.createBook(book);
        createdBooks.add(bookCreated);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdBooks));
        } catch (Exception error) {
            System.out.printf("[BookController.createBooks] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear la lista de libros"));
        }
    }
    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody BookDTO bookDTO){
        try {
            Book book = bookDTO.toEntity();
            Book bookCreated = bookService.createBook(
                   book);
        return ResponseEntity.created(URI.create("/books/" + bookCreated.getId())).body(bookCreated);
        } catch (Exception error) {
            System.out.printf("[BookController.createBook] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo crear el libro"));
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseData<?>> getBookById(@PathVariable Long bookId,
            @RequestParam(required = false, defaultValue = "true") Boolean activeOnly) {
        try {
            Book book;
            if (activeOnly) {
                book = bookService.getActiveBookById(bookId);
            } else {
                book = bookService.getBookById(bookId);
            }
            BookDTO bookDTO = book.toDTO();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(bookDTO));

        } catch (Exception error) {
            System.out.printf("[BookController.getBookById] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se encontro el producto"));
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseData<?>> updateBook(@RequestBody BookDTO bookDTO) {
        try {
        Book book = bookDTO.toEntity();
        Book updatedBook = bookService.updateBook(book);
        BookDTO updatedBookDTO = updatedBook.toDTO();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(updatedBookDTO));

        }catch (ProductException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[BookController.updateBook] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el libro"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<?>> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(null));

        } catch (Exception error) {
            System.out.printf("[ProductController.deleteProduct] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo eliminar el producto"));
        }
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseData<?>> updateBookStock(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) {
        bookService.updateStock(bookId,bookDTO.getStock());
        return getBookById(bookId, false);
    }

    // Endpoints de administración para activar/desactivar productos
    @PutMapping("/{bookId}/activate")
    public ResponseEntity<ResponseData<?>> activateBook(@PathVariable Long bookId) {
        try {
            bookService.activateBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Libro activado correctamente"));
        } catch (Exception error) {
            System.out.printf("[BookController.activateBook] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo activar el libro"));
        }
    }

    @PutMapping("/{bookId}/deactivate")
    public ResponseEntity<ResponseData<?>> deactivateBook(@PathVariable Long bookId) {
        try {
            bookService.deactivateBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Libro desactivado correctamente"));
        } catch (Exception error) {
            System.out.printf("[BookController.deactivateBook] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo desactivar el libro"));
        }
    }

    @PutMapping("/{bookId}/status")
    public ResponseEntity<ResponseData<?>> updateBookActiveStatus(@PathVariable Long bookId, @RequestBody Map<String, Boolean> status) {
        try {
            Boolean active = status.get("active");
            if (active == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("Se requiere el campo 'active'"));
            }
            bookService.updateActiveStatus(bookId, active);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Estado del libro actualizado correctamente"));
        } catch (Exception error) {
            System.out.printf("[BookController.updateBookActiveStatus] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el estado del libro"));
        }
    }

    // Endpoints for discount management
    @PutMapping("/{bookId}/discount")
    public ResponseEntity<ResponseData<?>> updateBookDiscount(@PathVariable Long bookId, @RequestBody Map<String, Object> discountData) {
        try {
            BigDecimal discountPercentage = null;
            Boolean discountActive = null;
            
            if (discountData.get("discountPercentage") != null) {
                if (discountData.get("discountPercentage") instanceof Number) {
                    discountPercentage = BigDecimal.valueOf(((Number) discountData.get("discountPercentage")).doubleValue());
                } else if (discountData.get("discountPercentage") instanceof String) {
                    discountPercentage = new BigDecimal((String) discountData.get("discountPercentage"));
                }
                
                // Validate discount percentage
                if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(90)) > 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("El porcentaje de descuento debe estar entre 0 y 90"));
                }
            }
            
            if (discountData.get("discountActive") != null) {
                discountActive = (Boolean) discountData.get("discountActive");
            }
            
            bookService.updateDiscount(bookId, discountPercentage, discountActive);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("Descuento del libro actualizado correctamente"));
        } catch (Exception error) {
            System.out.printf("[BookController.updateBookDiscount] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo actualizar el descuento del libro"));
        }
    }

}