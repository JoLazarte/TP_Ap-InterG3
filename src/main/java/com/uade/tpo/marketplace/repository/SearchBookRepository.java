package com.uade.tpo.marketplace.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.SearchBook;

@Repository
public interface SearchBookRepository extends JpaRepository<SearchBook, Long> {
    public List<SearchBook> findBooksByUserId(Long userId);
    void deleteByBookId(Long id);
}
