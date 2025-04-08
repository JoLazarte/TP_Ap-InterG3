package com.uade.tpo.marketplace.controllers;

import java.net.URI;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.entity.dto.PurchaseDocumentRequest;
import com.uade.tpo.marketplace.exceptions.PurchaseDocumentDuplicateException;
import com.uade.tpo.marketplace.service.PurchaseDocumentService;

@RestController
@RequestMapping("purchasedocuments")
public class PurchaseDocumentController {
    @Autowired
    private PurchaseDocumentService purchaseDocumentService;
    @GetMapping
    public ResponseEntity<Page<PurchaseDocument>> getPurchaseDocuments(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(purchaseDocumentService.getPurchaseDocuments(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(purchaseDocumentService.getPurchaseDocuments(PageRequest.of(page, size)));
    }
    @GetMapping("/{purchaseDocumentId}")
    public ResponseEntity<PurchaseDocument> getPurchaseDocumentById(@PathVariable Long purchaseDocumentId) {
        Optional<PurchaseDocument> result = purchaseDocumentService.getPurchaseDocumentById(purchaseDocumentId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Object> createPurchaseDocument(@RequestBody PurchaseDocumentRequest purchaseDocumentRequest)
            throws PurchaseDocumentDuplicateException {
        PurchaseDocument result = purchaseDocumentService.createPurchaseDocument(purchaseDocumentRequest.getDescription());
        return ResponseEntity.created(URI.create("/purchasedocuments/" + result.getId())).body(result);

    }
}
