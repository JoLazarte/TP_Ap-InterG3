package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;
import com.uade.tpo.marketplace.entity.Search;
import com.uade.tpo.marketplace.entity.User;

public interface SearchService {
    public List<Search> findAllSearchesByUserId(User authUser) throws Exception;
    public Search addBookSearch(User authUser, BookDTO bookDTO) throws Exception;
    public Search addMalbumSearch(User authUser, MusicAlbumDTO malbumDTO) throws Exception;
    public void emptySearches(User authUser) throws Exception;
}
