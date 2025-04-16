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
}