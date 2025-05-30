package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListItem;

public interface WishListItemService {
    public List<WishListItem> findAllWishListItemsByUserId(Long userId) throws Exception;
    public WishListItem addBookToWishList(User authUser, BookDTO bookDTO) throws Exception;
    public WishListItem addMalbumToWishList(User authUser, MusicAlbumDTO malbumDTO) throws Exception;
    public void removeBookFromWishList(User authUser, Long bookId) throws Exception;
    public void removeMalbumFromWishList(User authUser, Long malbumId) throws Exception;
    public void emptyWishList(User authUser) throws Exception;

}
