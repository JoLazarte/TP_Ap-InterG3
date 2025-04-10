package com.uade.tpo.marketplace.service;


import java.time.LocalDate;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.exceptions.PurchaseDocumentDuplicateException;


public interface PurchaseDocumentService {
   public Page<PurchaseDocument> getPurchaseDocuments(PageRequest pageRequest);


   public Optional<PurchaseDocument> getPurchaseDocumentById(Long purchaseDocumentId);


   public PurchaseDocument createPurchaseDocument(Buy buy, LocalDate purchaseDate, double totalPrice, String paymentMethod, String description ) throws PurchaseDocumentDuplicateException;
}


