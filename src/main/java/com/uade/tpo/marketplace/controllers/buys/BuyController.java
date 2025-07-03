package com.uade.tpo.marketplace.controllers.buys;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.BuyService;
import com.uade.tpo.marketplace.service.UserService;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.MusicAlbumService;


@RestController
@RequestMapping("/buys")
public class BuyController {

    @Autowired
    private BuyService buyService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private MusicAlbumService musicAlbumService;

    @GetMapping("/userBuys")
    public ResponseEntity<ResponseData<?>> getUserBuys(@AuthenticationPrincipal UserDetails userDetails) {
    try {
      User authUser = userService.getUserByUsername(userDetails.getUsername());

      List<BuyDTO> buys = buyService.getUserBuys(authUser.getId()).stream().map(Buy::toDTO).toList();

      return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(buys));

    } catch (UserException error) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));

    } catch (Exception error) {
      System.out.printf("[BuyController.getUserBuys] -> %s", error.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseData.error("No se pudieron obtener las compras."));
    }
  }

    @PostMapping("/create")
    public ResponseEntity<ResponseData<?>> createBuy(@AuthenticationPrincipal UserDetails userDetails, 
                                                    @RequestBody CreateBuyRequest request) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            
            List<BuyItem> items = request.getItems().stream()
                .map(item -> {
                    BuyItem buyItem = BuyItem.builder()
                        .finalPrice(item.getFinalPrice())
                        .totalQuantity(item.getTotalQuantity())
                        .build();
                    
                    if (item.getBookId() != null) {
                        try {
                            Book book = bookService.getBookById(item.getBookId());
                            buyItem.setBook(book);
                        } catch (Exception e) {
                            throw new RuntimeException("Libro no encontrado con ID: " + item.getBookId());
                        }
                    } else if (item.getMusicAlbumId() != null) {
                        try {
                            MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(item.getMusicAlbumId());
                            buyItem.setMusicAlbum(musicAlbum);
                        } catch (Exception e) {
                            throw new RuntimeException("Álbum de música no encontrado con ID: " + item.getMusicAlbumId());
                        }
                    }
                    
                    return buyItem;
                })
                .collect(Collectors.toList());
            
            Buy createdBuy = buyService.createBuy(authUser, items, false);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseData.success(createdBuy.toDTO()));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[BuyController.createBuy] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo crear la compra."));
        }
    }

    @PutMapping("/{buyId}/empty")
    public ResponseEntity<ResponseData<?>> emptyBuy(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable Long buyId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            
            // Verificar que la compra pertenece al usuario autenticado
            List<Buy> userBuys = buyService.getUserBuys(authUser.getId());
            boolean buyBelongsToUser = userBuys.stream()
                .anyMatch(buy -> buy.getId().equals(buyId));
            
            if (!buyBelongsToUser) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseData.error("No tienes permisos para vaciar esta compra."));
            }
            
            buyService.emptyBuy(buyId);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseData.success("Compra vaciada correctamente."));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[BuyController.emptyBuy] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo vaciar la compra."));
        }
    }

    @PutMapping("/{buyId}/confirm")
    public ResponseEntity<ResponseData<?>> confirmBuy(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable Long buyId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            
            // Verificar que la compra pertenece al usuario autenticado
            List<Buy> userBuys = buyService.getUserBuys(authUser.getId());
            boolean buyBelongsToUser = userBuys.stream()
                .anyMatch(buy -> buy.getId().equals(buyId));
            
            if (!buyBelongsToUser) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseData.error("No tienes permisos para confirmar esta compra."));
            }
            
            Buy confirmedBuy = buyService.confirmBuy(buyId);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseData.success(confirmedBuy.toDTO()));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            // Verificar si es un error de stock insuficiente
            if (error.getMessage().contains("Stock insuficiente")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.error(error.getMessage()));
            }
            
            System.out.printf("[BuyController.confirmBuy] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseData.error("No se pudo confirmar la compra."));
        }
    }
    
}
