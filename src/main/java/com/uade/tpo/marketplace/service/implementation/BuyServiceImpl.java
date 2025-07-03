package com.uade.tpo.marketplace.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.BuyItem;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.BuyRepository;
import com.uade.tpo.marketplace.service.BuyService;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.MusicAlbumService;

import jakarta.transaction.Transactional;

@Service
public class BuyServiceImpl implements BuyService{
   
    @Autowired
    private BuyRepository buyRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private MusicAlbumService musicAlbumService;

    @Transactional
    public List<Buy> getUserBuys(Long userId) throws Exception {
        try{
            return buyRepository.findByUserId(userId);
          }catch(Exception error) {
            throw new Exception("[BuyService.getUserBuys] -> " + error.getMessage());
          }
    }

    @Transactional
    public Buy createBuy(User user, List<BuyItem> items, boolean confirmed) throws Exception {
        try {
            Buy buy = Buy.builder()
                .buyDate(LocalDateTime.now())
                .user(user)
                .confirmed(false)
                .build();

            buy.setItems(items);

            return buyRepository.save(buy);

        } catch(Exception error) {
            throw new Exception("[BuyService.createBuy] -> " + error.getMessage());
        }
    }

    @Transactional
    public void emptyBuy(Long buyId) throws Exception {
        try {
            Buy buy = buyRepository.findById(buyId)
                .orElseThrow(() -> new Exception("No se encontró la compra con ID: " + buyId));
            
            // Eliminar todos los items de la compra
            buy.getItems().clear();
            
            // Guardar la compra actualizada
            buyRepository.save(buy);
            
        } catch(Exception error) {
            throw new Exception("[BuyService.emptyBuy] -> " + error.getMessage());
        }
    }

    @Transactional
    public Buy confirmBuy(Long buyId) throws Exception {
        try {
            Buy buy = buyRepository.findById(buyId)
                .orElseThrow(() -> new Exception("No se encontró la compra con ID: " + buyId));
            
            // Validar stock para cada item
            for (BuyItem item : buy.getItems()) {
                if (item.getBook() != null) {
                    // Validar stock de libro
                    Book book = item.getBook();
                    if (book.getStock() < item.getTotalQuantity()) {
                        throw new Exception("Stock insuficiente para el libro '" + book.getTitle() + 
                            "'. Stock disponible: " + book.getStock() + ", Cantidad solicitada: " + item.getTotalQuantity());
                    }
                } else if (item.getMusicAlbum() != null) {
                    // Validar stock de álbum de música
                    MusicAlbum musicAlbum = item.getMusicAlbum();
                    if (musicAlbum.getStock() < item.getTotalQuantity()) {
                        throw new Exception("Stock insuficiente para el álbum '" + musicAlbum.getTitle() + 
                            "'. Stock disponible: " + musicAlbum.getStock() + ", Cantidad solicitada: " + item.getTotalQuantity());
                    }
                }
            }
            
            // Actualizar stock (restar la cantidad comprada)
            for (BuyItem item : buy.getItems()) {
                if (item.getBook() != null) {
                    Book book = item.getBook();
                    book.setStock(book.getStock() - item.getTotalQuantity());
                    bookService.updateBook(book);
                } else if (item.getMusicAlbum() != null) {
                    MusicAlbum musicAlbum = item.getMusicAlbum();
                    musicAlbum.setStock(musicAlbum.getStock() - item.getTotalQuantity());
                    musicAlbumService.updateMalbum(musicAlbum);
                }
            }
            
            // Cambiar el estado a confirmado
            buy.setConfirmed(true);
            
            // Guardar la compra actualizada
            return buyRepository.save(buy);
            
        } catch(Exception error) {
            throw new Exception("[BuyService.confirmBuy] -> " + error.getMessage());
        }
    }

    
}