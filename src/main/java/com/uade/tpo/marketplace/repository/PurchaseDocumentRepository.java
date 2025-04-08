package com.uade.tpo.marketplace.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;   
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseDocumentRepository extends JpaRepository<PurchaseDocument, Long> {
    @Query(value = "select p from PurchaseDocument p where p.description = ?1")
    List<PurchaseDocument> findByDescription(String description);
}
