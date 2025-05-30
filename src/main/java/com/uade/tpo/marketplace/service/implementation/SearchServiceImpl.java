package com.uade.tpo.marketplace.service.implementation;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;
import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.entity.Search;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.BookRepository;
import com.uade.tpo.marketplace.repository.MusicAlbumRepository;
import com.uade.tpo.marketplace.repository.UserRepository;
import com.uade.tpo.marketplace.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
    
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private MusicAlbumRepository malbumRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Search> findAllSearchesByUserId(User authUser) throws Exception {
    try {
      List<Search> searches = authUser.getLastSearches();
      Collections.reverse(searches);
      return searches;
    } catch (Exception error) {
      throw new Exception("[SearchItemService.findAllSearchesByUserId] -> " + error.getMessage());
    }
  }

  public Search addBookSearch(User authUser, BookDTO bookDTO) throws Exception {
    try {
      Book book = bookRepository.findById(bookDTO.getId())
          .orElseThrow(() -> new ProductException("Producto no encontrado"));

      List<Search> searches = authUser.getLastSearches();
      if (searches.size() >= 10) {
        searches.remove(0);
      }

      Search search = Search.builder().user(authUser).book(book).date(LocalDateTime.now()).build();
      searches.add(search);
      userRepository.save(authUser);
      return search;
    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[SearchItemService.addSearch] -> " + error.getMessage());
    }
  }
  public Search addMalbumSearch(User authUser, MusicAlbumDTO malbumDTO) throws Exception {
    try {
      MusicAlbum malbum = malbumRepository.findById(malbumDTO.getId())
          .orElseThrow(() -> new ProductException("Producto no encontrado"));

      List<Search> searches = authUser.getLastSearches();
      if (searches.size() >= 10) {
        searches.remove(0);
      }

      Search search = Search.builder().user(authUser).malbum(malbum).date(LocalDateTime.now()).build();
      searches.add(search);
      userRepository.save(authUser);
      return search;
    } catch (ProductException error) {
      throw new ProductException(error.getMessage());
    } catch (Exception error) {
      throw new Exception("[SearchItemService.addSearch] -> " + error.getMessage());
    }
  }

  public void emptySearches(User authUser) throws Exception {
    try {
      authUser.getLastSearches().clear();
      userRepository.save(authUser);
    } catch (Exception error) {
      throw new Exception("[SearchItemService.emptySearches] -> " + error.getMessage());
    }
  }
}