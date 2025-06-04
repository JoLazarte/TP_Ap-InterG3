package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;
import com.uade.tpo.marketplace.entity.SearchBook;
import com.uade.tpo.marketplace.entity.SearchMusicAlbum;
import com.uade.tpo.marketplace.entity.User;

public interface SearchService {
    public List<SearchBook> findBookSearchesByUserId(User authUser) throws Exception;
    public List<SearchMusicAlbum> findMalbumSearchesByUserId(User authUser) throws Exception;
    public SearchBook addBookSearch(User authUser, BookDTO bookDTO) throws Exception;
    public SearchMusicAlbum addMalbumSearch(User authUser, MusicAlbumDTO malbumDTO) throws Exception;
    public void emptySearches(User authUser) throws Exception;
}
