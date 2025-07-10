package com.uade.tpo.marketplace.service.implementation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.MusicAlbumDuplicateException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.MusicAlbumRepository;
import com.uade.tpo.marketplace.repository.SearchMusicAlbumRepository;
import com.uade.tpo.marketplace.repository.WishListMusicAlbumRepository;
import com.uade.tpo.marketplace.service.MusicAlbumService;

import jakarta.transaction.Transactional;

@Service
public class MusicAlbumServiceImpl implements MusicAlbumService {

    @Autowired
    private MusicAlbumRepository musicAlbumRepository;
    @Autowired
    private WishListMusicAlbumRepository wishListMalbumRepository;
 
    @Autowired
    private SearchMusicAlbumRepository searchMusicAlbumRepository;

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
              searchMusicAlbumRepository.deleteByMalbumId(malbumId);
              wishListMalbumRepository.deleteByMalbumId(malbumId);
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
    
    // Implementación de métodos para filtrar productos activos
    public Page<MusicAlbum> getActiveMusicAlbums(PageRequest pageable) {
        return musicAlbumRepository.findAllActive(pageable);
    }
    
    public Page<MusicAlbum> getActiveMusicAlbumsByAuthor(String author, PageRequest pageable) {
        return musicAlbumRepository.findByAuthorAndActive(author, pageable);
    }
    
    public MusicAlbum getActiveMusicAlbumById(Long musicAlbumId) throws Exception {
        try {
            MusicAlbum musicAlbum = musicAlbumRepository.findById(musicAlbumId).orElseThrow(() -> new ProductException("producto no encontrado"));
            if (!musicAlbum.isActive()) {
                throw new ProductException("El producto no está activo");
            }
            return musicAlbum;
          } catch (Exception error) {
            throw new Exception("[MusicAlbumServiceImpl.getActiveMusicAlbumById] -> " + error.getMessage());
          }
    }
    
    // Métodos de administración para activar/desactivar productos
    @Transactional
    public void activateMusicAlbum(Long musicAlbumId) throws Exception {
        try {
            if (!musicAlbumRepository.existsById(musicAlbumId)) {
                throw new ProductException("El álbum musical con id: '" + musicAlbumId + "' no existe.");
            }
            musicAlbumRepository.updateActiveStatus(musicAlbumId, true);
        } catch (Exception error) {
            throw new Exception("[MusicAlbumServiceImpl.activateMusicAlbum] -> " + error.getMessage());
        }
    }
    
    @Transactional
    public void deactivateMusicAlbum(Long musicAlbumId) throws Exception {
        try {
            if (!musicAlbumRepository.existsById(musicAlbumId)) {
                throw new ProductException("El álbum musical con id: '" + musicAlbumId + "' no existe.");
            }
            musicAlbumRepository.updateActiveStatus(musicAlbumId, false);
        } catch (Exception error) {
            throw new Exception("[MusicAlbumServiceImpl.deactivateMusicAlbum] -> " + error.getMessage());
        }
    }
    
    @Transactional
    public void updateActiveStatus(Long musicAlbumId, boolean active) throws Exception {
        try {
            if (!musicAlbumRepository.existsById(musicAlbumId)) {
                throw new ProductException("El álbum musical con id: '" + musicAlbumId + "' no existe.");
            }
            musicAlbumRepository.updateActiveStatus(musicAlbumId, active);
        } catch (Exception error) {
            throw new Exception("[MusicAlbumServiceImpl.updateActiveStatus] -> " + error.getMessage());
        }
    }
    
    // Métodos para gestión de descuentos
    @Transactional
    public void updateDiscount(Long musicAlbumId, BigDecimal discountPercentage, Boolean discountActive) throws Exception {
        try {
            if (!musicAlbumRepository.existsById(musicAlbumId)) {
                throw new ProductException("El álbum musical con id: '" + musicAlbumId + "' no existe.");
            }
            
            MusicAlbum musicAlbum = musicAlbumRepository.findById(musicAlbumId).orElseThrow(() -> new ProductException("Álbum musical no encontrado"));
            
            if (discountPercentage != null) {
                // Validate discount percentage
                if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(90)) > 0) {
                    throw new ProductException("El porcentaje de descuento debe estar entre 0 y 90");
                }
                musicAlbum.setDiscountPercentage(discountPercentage);
            }
            
            if (discountActive != null) {
                musicAlbum.setDiscountActive(discountActive);
            }
            
            musicAlbumRepository.save(musicAlbum);
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[MusicAlbumServiceImpl.updateDiscount] -> " + error.getMessage());
        }
    }

}