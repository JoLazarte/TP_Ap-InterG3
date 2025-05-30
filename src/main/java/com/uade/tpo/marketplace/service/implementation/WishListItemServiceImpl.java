package com.uade.tpo.marketplace.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListItem;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.exceptions.WishListException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.repository.MusicAlbumRepository;

import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.repository.WishListItemRepository;
import com.uade.tpo.marketplace.service.WishListItemService;

@Service
public class WishListItemServiceImpl implements WishListItemService {
    @Autowired
    private WishListItemRepository wishListItemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MusicAlbumRepository malbumRepository;
    @Autowired
    private UserRepository userRepository;

    public List<WishListItem> findAllWishListItemsByUserId(Long userId) throws Exception {
        try {
      return wishListItemRepository.findAllByUserId(userId);

    } catch (Exception error) {
      throw new Exception("[WishListItemService.findAllWishListItemsByUserId] -> " + error.getMessage());
    }
  }

  public WishListItem addBookToWishList(User authUser, BookDTO bookDTO) throws Exception {
    // Obtener el producto por ID
    try {
      Book book = bookRepository.findById(bookDTO.getId())
          .orElseThrow(() -> new ProductException("Producto no encontrado"));
      List<WishListItem> wishlist = authUser.getWishList();
      boolean isBookInWishlist = wishlist.stream()
          .anyMatch(item -> item.getBook().getId().equals(book.getId()));

      if (isBookInWishlist) {
        throw new WishListException("El producto ya está en la wishlist.");
      }

      WishListItem wishListItem = WishListItem.builder().user(authUser).book(book).build();
      wishlist.add(wishListItem);

      userRepository.save(authUser);
      return wishListItem;
    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (WishListException error) {
      throw new WishListException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[WishListItemService.addProductToWishList] -> " + error.getMessage());
    }

  }

  public WishListItem addMalbumToWishList(User authUser, MusicAlbumDTO malbumDTO) throws Exception {
    // Obtener el producto por ID
    try {
      MusicAlbum malbum = malbumRepository.findById(malbumDTO.getId())
          .orElseThrow(() -> new ProductException("Producto no encontrado"));
      List<WishListItem> wishlist = authUser.getWishList();
      boolean isMalbumInWishlist = wishlist.stream()
          .anyMatch(item -> item.getMalbum().getId().equals(malbum.getId()));

      if (isMalbumInWishlist) {
        throw new WishListException("El producto ya está en la wishlist.");
      }

      WishListItem wishListItem = WishListItem.builder().user(authUser).malbum(malbum).build();
      wishlist.add(wishListItem);

      userRepository.save(authUser);
      return wishListItem;
    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (WishListException error) {
      throw new WishListException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[WishListItemService.addProductToWishList] -> " + error.getMessage());
    }

  }

  public void removeBookFromWishList(User authUser, Long bookId) throws Exception {
    // Obtener el producto por ID
    try {
      Book book = bookRepository.findById(bookId)
          .orElseThrow(() -> new ProductException("Producto no encontrado"));

      List<WishListItem> wishlist = authUser.getWishList();
      WishListItem itemToRemove = wishlist.stream()
          .filter(item -> item.getBook().getId().equals(book.getId()))
          .findFirst()
          .orElseThrow(() -> new WishListException("El producto no está en la wishlist."));

      wishlist.remove(itemToRemove);
      userRepository.save(authUser);

    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (WishListException error) {
      throw new WishListException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[WishListItemService.addProductToWishList] -> " + error.getMessage());
    }

  }
  public void removeMalbumFromWishList(User authUser, Long malbumId) throws Exception {
    // Obtener el producto por ID
    try {
      MusicAlbum malbum = malbumRepository.findById(malbumId)
          .orElseThrow(() -> new ProductException("Producto no encontrado"));

      List<WishListItem> wishlist = authUser.getWishList();
      WishListItem itemToRemove = wishlist.stream()
          .filter(item -> item.getMalbum().getId().equals(malbum.getId()))
          .findFirst()
          .orElseThrow(() -> new WishListException("El producto no está en la wishlist."));

      wishlist.remove(itemToRemove);
      userRepository.save(authUser);

    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (WishListException error) {
      throw new WishListException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[WishListItemService.addProductToWishList] -> " + error.getMessage());
    }

  }

  public void emptyWishList(User authUser) throws Exception {
    try {
      authUser.getWishList().clear();
      userRepository.save(authUser);
    } catch (Exception error) {
      throw new Exception("[WishListItemService.emptyWishList] -> " + error.getMessage());
    }
  }

}