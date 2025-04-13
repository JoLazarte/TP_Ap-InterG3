package com.uade.tpo.marketplace.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.marketplace.entity.PurchaseDocument;



public interface PurchaseDocumentService {

   public List<PurchaseDocument> getBuysPurchDocs(Long userId) throws Exception;

   public Optional<PurchaseDocument> getPurchaseDocumentById(Long purchaseDocumentId);

   public PurchaseDocument createPurchaseDocument()throws Exception;
}


