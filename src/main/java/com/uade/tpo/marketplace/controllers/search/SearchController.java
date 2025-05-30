package com.uade.tpo.marketplace.controllers.search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.marketplace.controllers.books.BookDTO;
import com.uade.tpo.marketplace.controllers.musicalbums.MusicAlbumDTO;
import com.uade.tpo.marketplace.entity.ResponseData;
import com.uade.tpo.marketplace.entity.Search;
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.service.SearchService;
import com.uade.tpo.marketplace.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserService userService;

    // Obtener todas las búsquedas de un usuario
    @GetMapping("")
    public ResponseEntity<?> getUserSearches(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            List<Search> searches = searchService.findAllSearchesByUserId(authUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseData.success(searches.stream().map(Search::toDTO).toList()));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[SearchController.getUserSearches] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo recuperar las búsquedas del usuario"));
        }
    }

    // Añadir una nueva búsqueda
    @PutMapping("/books")
    public ResponseEntity<?> addBookSearch(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookDTO bookDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Search search = searchService.addBookSearch(authUser, bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(search.toDTO()));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[SearchController.addSearch] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo registrar la busqueda del usuario"));
        }
    }
    @PutMapping("/musicAlbums")
    public ResponseEntity<?> addMalbumSearch(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MusicAlbumDTO malbumDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            Search search = searchService.addMalbumSearch(authUser, malbumDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(search.toDTO()));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[SearchController.addSearch] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo registrar la busqueda del usuario"));
        }
    }

    // Vaciar todas las búsquedas de un usuario
    @PutMapping("/empty")
    public ResponseEntity<?> emptySearches(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            searchService.emptySearches(authUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseData.success("Búsquedas eliminadas correctamente!"));
        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[SearchController.emptySearches] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo vaciar el historial del usuario"));
        }
    }
}