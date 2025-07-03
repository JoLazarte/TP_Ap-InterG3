package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select b from Book b where b.isbn = ?1")
    List<Book> findByIsbn(String isbn);

    @Query(value = "select b from Book b where b.author = ?1")
    PageImpl<Book> findByAuthor(String author, Pageable page);

    @Modifying
    @Query(value = "update Book b set b.stock = ?2 where b.id = ?1")
    void updateStock(Long id, int newStock);

    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Consultas que filtran solo productos activos
    @Query(value = "select b from Book b where b.active = true")
    List<Book> findAllActive();
    
    @Query(value = "select b from Book b where b.active = true")
    PageImpl<Book> findAllActive(Pageable page);
    
    @Query(value = "select b from Book b where b.isbn = ?1 and b.active = true")
    List<Book> findByIsbnAndActive(String isbn);

    @Query(value = "select b from Book b where b.author = ?1 and b.active = true")
    PageImpl<Book> findByAuthorAndActive(String author, Pageable page);

    List<Book> findByTitleContainingIgnoreCaseAndActive(String title, boolean active);
    
    // Método para activar/desactivar productos
    @Modifying
    @Query(value = "update Book b set b.active = ?2 where b.id = ?1")
    void updateActiveStatus(Long id, boolean active);
}