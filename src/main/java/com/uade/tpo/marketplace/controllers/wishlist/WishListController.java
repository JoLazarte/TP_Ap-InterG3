package com.uade.tpo.marketplace.controllers.wishlist;

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
import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.WishListBook;
import com.uade.tpo.marketplace.entity.WishListMusicAlbum;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.exceptions.UserException;
import com.uade.tpo.marketplace.exceptions.WishListException;
import com.uade.tpo.marketplace.service.UserService;
import com.uade.tpo.marketplace.service.WishListService;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public ResponseEntity<?> getUserBookWishList(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());

            List<WishListBook> wishList = wishListService.findAllWishListBooksByUserId(authUser.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseData.success(wishList.stream().map(WishListBook::toDTO).toList()));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.getUserWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo recuperar la wishlist del usuario"));
        }

    }
    @GetMapping("/musicAlbums")
    public ResponseEntity<?> getUserMalbumWishList(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());

            List<WishListMusicAlbum> wishList = wishListService.findAllWishListMalbumsByUserId(authUser.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseData.success(wishList.stream().map(WishListMusicAlbum::toDTO).toList()));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.getUserWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo recuperar la wishlist del usuario"));
        }

    }
    @PutMapping("/books/{bookId}")
    public ResponseEntity<?> addBookToWishList(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookDTO bookDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            WishListBook item = wishListService.addBookToWishList(authUser, bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(item.toDTO()));

        } catch (UserException | ProductException | WishListException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.error("No se pudo agregar el producto a la wishlist"));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.addProductToWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error(error.getMessage()));
        }
    }
    @PutMapping("/musicAlbum/{musicAlbumId}")
    public ResponseEntity<?> addMalbumToWishList(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MusicAlbumDTO malbumDTO) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            WishListMusicAlbum item = wishListService.addMalbumToWishList(authUser, malbumDTO);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(item.toDTO()));

        } catch (UserException | ProductException | WishListException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseData.error("No se pudo agregar el producto a la wishlist"));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.addProductToWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error(error.getMessage()));
        }
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> removeBookFromWishList(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long bookId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            wishListService.removeBookFromWishList(authUser, bookId);
            List<WishListBookDTO> wishlist = authUser.getWishListBooks().stream().map(WishListBook::toDTO).toList();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(wishlist));

        } catch (UserException | ProductException | WishListException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.addProductToWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo recuperar la wishlist del usuario"));
        }
    }
    @PutMapping("/{musicAlbumId}")
    public ResponseEntity<?> removeMalbumFromWishList(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long malbumId) {
        try {
            User authUser = userService.getUserByUsername(userDetails.getUsername());
            wishListService.removeMalbumFromWishList(authUser, malbumId);
            List<WishListMusicAlbumDTO> wishlist = authUser.getWishListMalbums().stream().map(WishListMusicAlbum::toDTO).toList();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(wishlist));

        } catch (UserException | ProductException | WishListException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));

        } catch (Exception error) {
            System.out.printf("[WishListItemController.addProductToWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseData.error("No se pudo recuperar la wishlist del usuario"));
        }
    }

    @PutMapping("/empty")
    public ResponseEntity<?> emptyWishList(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            wishListService.emptyWishList(userService.getUserByUsername(userDetails.getUsername()));

            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success("WishList vaciada correctamente!"));

        } catch (UserException | WishListException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error(error.getMessage()));
        } catch (Exception error) {
            System.out.printf("[WishListItemController.emptyWishList] -> %s", error.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo vaciar la wishlist del usuario");
        }

    }
}