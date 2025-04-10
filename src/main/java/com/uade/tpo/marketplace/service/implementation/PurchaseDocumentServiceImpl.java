package com.uade.tpo.marketplace.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.exceptions.PurchaseDocumentDuplicateException;
import com.uade.tpo.marketplace.repository.PurchaseDocumentRepository;
import com.uade.tpo.marketplace.service.PurchaseDocumentService;

@Service
public class PurchaseDocumentServiceImpl implements PurchaseDocumentService {
   @Autowired
   private PurchaseDocumentRepository purchaseDocumentRepository;
   public Page<PurchaseDocument> getPurchaseDocuments(PageRequest pageable) {
       return purchaseDocumentRepository.findAll(pageable);
   }
   public Optional<PurchaseDocument> getPurchaseDocumentById(long purchaseId){
       return purchaseDocumentRepository.findById(purchaseId);
   }
   public PurchaseDocument createPurchaseDocument(Buy buy, LocalDate purchaseDate, double totalPrice, String paymentMethod, String description) throws PurchaseDocumentDuplicateException {
       List<PurchaseDocument> purchaseDocuments = purchaseDocumentRepository.findByDescription(description);
       if (purchaseDocuments.isEmpty())
           return purchaseDocumentRepository.save(new PurchaseDocument(
                           buy,
                           purchaseDate,
                           totalPrice,
                           paymentMethod,
                           description
           ));
       throw new PurchaseDocumentDuplicateException();
   }
   @Override
   public Optional<PurchaseDocument> getPurchaseDocumentById(Long purchaseDocumentId) {
       return purchaseDocumentRepository.findById(purchaseDocumentId);
   }
}
