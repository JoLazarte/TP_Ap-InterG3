package com.uade.tpo.marketplace.controllers.purchasedocuments;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.PurchaseDocument;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.PurchaseDocumentService;
import com.uade.tpo.marketplace.service.UserService;

@RestController
@RequestMapping("/purchaseDocuments")
public class PurchaseDocumentController {
    @Autowired
    private PurchaseDocumentService purchaseDocumentService;
   
    @Autowired
    private UserService userService;

    @GetMapping("/userBuys")
    public ResponseEntity<ResponseData<?>> getBuysPurchDocs(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());

            //List<BuyDTO> buys = buyService.getUserBuys(authUser.getId()).stream().map(Buy::toDTO).toList();

            List<PurchaseDocumentDTO> purchaseDocumentDTOs = purchaseDocumentService.getBuysPurchDocs(authUser.getId()).stream().map(PurchaseDocument::toDTO).toList();

            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(purchaseDocumentDTOs));

             } catch (UserException error) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));

            } catch (Exception error) {
                System.out.printf("[PurchaseDocumentController.getBuysPurchDocs] -> %s", error.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudieron obtener los documentos de compra."));
            }
    }
    

}
