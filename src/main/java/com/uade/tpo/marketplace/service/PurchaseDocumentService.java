package com.uade.tpo.marketplace.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.exceptions.PurchaseDocumentDuplicateException;

public interface PurchaseDocumentService {
    public Page<PurchaseDocument> getPurchaseDocuments(PageRequest pageRequest);

    public Optional<PurchaseDocument> getPurchaseDocumentById(Long purchaseDocumentId);

    public PurchaseDocument createPurchaseDocument(String description) throws PurchaseDocumentDuplicateException;
}