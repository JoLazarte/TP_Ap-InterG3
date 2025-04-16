package com.uade.tpo.marketplace.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.repository.PurchaseDocumentRepository;
import com.uade.tpo.marketplace.service.PurchaseDocumentService;

@Service
public class PurchaseDocumentServiceImpl implements PurchaseDocumentService {

    @Autowired
    private PurchaseDocumentRepository purchaseDocumentRepository;
    
    public List<PurchaseDocument> getBuysPurchDocs(Long userId) throws Exception {

        try{
            return purchaseDocumentRepository.findByUserId(userId);
          }catch(Exception error) {
            throw new Exception("[PurchaseDocumentService.getBuysPurchDocs] -> " + error.getMessage());
        }
    }

    public Optional<PurchaseDocument> getPurchaseDocumentById(Long purchaseDocumentId){
       return purchaseDocumentRepository.findById(purchaseDocumentId);
    }

    public PurchaseDocument createPurchaseDocument() throws Exception {
        try {
          PurchaseDocument purchaseDocument = new PurchaseDocument();
          return purchaseDocument;
        } catch (Exception error) {
          throw new Exception("[CartService.createCart] -> " + error.getMessage());
        }
    }


}
