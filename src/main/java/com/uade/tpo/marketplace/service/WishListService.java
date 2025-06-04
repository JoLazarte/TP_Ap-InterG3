package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListBook;
import com.uade.tpo.marketplace.entity.WishListMusicAlbum;

public interface WishListService {
    public List<WishListMusicAlbum> findAllWishListMalbumsByUserId(Long userId) throws Exception; 
    public List<WishListBook> findAllWishListBooksByUserId(Long userId) throws Exception;
    public WishListBook addBookToWishList(User authUser, BookDTO bookDTO) throws Exception;
    public WishListMusicAlbum addMalbumToWishList(User authUser, MusicAlbumDTO malbumDTO) throws Exception;
    public void removeBookFromWishList(User authUser, Long bookId) throws Exception;
    public void removeMalbumFromWishList(User authUser, Long malbumId) throws Exception;
    public void emptyWishList(User authUser) throws Exception;

}
