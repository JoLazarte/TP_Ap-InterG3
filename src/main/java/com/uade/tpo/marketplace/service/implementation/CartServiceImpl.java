package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.CartException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.CartService;
import com.uade.tpo.marketplace.service.MusicAlbumService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private BookService bookService;
    @Autowired
    private MusicAlbumService musicAlbumService;

    @Override
    public Optional<Cart> getCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Override
    public Cart createCart() throws Exception {
        try {
          Cart cart = new Cart();
          return cart;
        } catch (Exception error) {
          throw new Exception("[CartService.createCart] -> " + error.getMessage());
        }
      }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    @Transactional
    public CartItem addItemBook(Cart cart, Long bookId) throws Exception {
        try {

            Book book = bookService.getBookById(bookId);
            if (book.getStock() == 0) {
                throw new ProductException("Se acabo el stock del producto seleccionado");
            }
            CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(book.getId()))
                .findFirst()
                .orElse(null);

            if (cartItem != null) {
            // Si ya existe, actualizamos la cantidad
            if (book.getStock() < cartItem.getQuantity() + 1) {
                throw new ProductException("No hay stock suficiente del producto elegido");
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            }

            } else {
            // Si no existe, creamos un nuevo ítem
                cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setQuantity(1);
                cartItem.setCart(cart);

            // Agregamos el nuevo ítem al carrito
            cart.getItems().add(cartItem);
            }

            // Guardar el carrito actualizado
            cartRepository.save(cart);
            return cartItem;
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[CartService.addItemToCart] -> " + error.getMessage());
        }
    }
    
   
    @Override
    @Transactional
    public CartItem addItemMusicAlbum(Cart cart, Long musicAlbumId) throws Exception {
        try {
            MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(musicAlbumId);
            if (musicAlbum.getStock() == 0) {
                throw new ProductException("Se acabo el stock del producto seleccionado");
            }
            CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getBookId().equals(musicAlbum.getId()))
                .findFirst()
                .orElse(null);

            if (cartItem != null) {
            // Si ya existe, actualizamos la cantidad
            if (musicAlbum.getStock() < cartItem.getQuantity() + 1) {
                throw new ProductException("No hay stock suficiente del producto elegido");
                } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                }

            } else {
                // Si no existe, creamos un nuevo ítem
                cartItem = new CartItem();
                cartItem.setMusicAlbum(musicAlbum);
                cartItem.setQuantity(1);
                cartItem.setCart(cart);

            // Agregamos el nuevo ítem al carrito
            cart.getItems().add(cartItem);
        }

            // Guardar el carrito actualizado
            cartRepository.save(cart);
            return cartItem;
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
                throw new Exception("[CartService.addItemToCart] -> " + error.getMessage());
        }
    }

    @Override
    @Transactional
    public float calculateTotal(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            return calculateCartTotal(cart);
        }
        return 0f;
    }

    private float calculateCartTotal(Cart cart) {
        float total = 0f;
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                total += item.calculateTotalBook() + item.calculateTotalMusicAlbum();
            }
        }
        return total;
    }
    
}