package com.uade.tpo.marketplace.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.repository.MusicAlbumRepository;

import com.uade.tpo.marketplace.service.MusicAlbumService;

import jakarta.transaction.Transactional;

@Service
public class MusicAlbumServiceImpl implements MusicAlbumService {

    @Autowired
    private MusicAlbumRepository musicAlbumRepository;
    @Autowired
    private CartRepository cartRepository;

    public Page<MusicAlbum> getMusicAlbums(PageRequest pageable) {
        return musicAlbumRepository.findAll(pageable);
    }
   
    public MusicAlbum getMusicAlbumById(Long musicAlbumId) throws Exception {
        try {
            MusicAlbum musicAlbum = musicAlbumRepository.findById(musicAlbumId).orElseThrow(() -> new ProductException("producto no encontrado"));
            return musicAlbum;
          } catch (Exception error) {
            throw new Exception("[ProductService.getProductById] -> " + error.getMessage());
          }
    }

    public Page<MusicAlbum> getMusicAlbumByAuthor(String author, PageRequest pageable) {
        return musicAlbumRepository.findByAuthor(author, pageable);
    }

    public MusicAlbum createMusicAlbum(MusicAlbum musicAlbum) throws MusicAlbumDuplicateException {
        List<MusicAlbum> musicAlbums = musicAlbumRepository.findByIsrc(musicAlbum.getIsrc());
        if (musicAlbums.isEmpty())
            return musicAlbumRepository.save(musicAlbum); 
        throw new MusicAlbumDuplicateException();
    }

    public MusicAlbum updateMalbum(MusicAlbum musicAlbum) throws Exception {
          try {
            if (!musicAlbumRepository.existsById(musicAlbum.getId())) 
              throw new ProductException("El disco con id: '" + musicAlbum.getId() + "' no existe.");
            
            MusicAlbum updatedMalbum = musicAlbumRepository.save(musicAlbum);
            return updatedMalbum;
          } catch (ProductException error) {
            throw new ProductException(error.getMessage());
          } catch (Exception error) {
            throw new Exception("[MusicAlbumService.updateMalbum] -> " + error.getMessage());
          }
    }

    @Transactional
    public void deleteMalbum(Long malbumId) throws Exception {
        try {
            List<Cart> carts = cartRepository.findAll();
              for (Cart cart : carts) {
                  cart.getItems().removeIf(item -> item.getMusicAlbum().getId().equals(malbumId));
              }
              cartRepository.saveAll(carts);

              //searchRepository.deleteByProductId(bookId);
              //wishListItemRepository.deleteByProductId(bookId);
              musicAlbumRepository.deleteById(malbumId);

          } catch (Exception error) {
            throw new Exception("[MusicAlbumService.deleteMalbum] -> " + error.getMessage());
          }
    }

    //public List<MusicAlbum> getMusicAlbumByTitle(String title) {
    //}

    public void updateStock(Long musicAlbumId, int newStock) {
        musicAlbumRepository.updateStock(musicAlbumId, newStock);
    }

}
