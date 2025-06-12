package com.uade.tpo.marketplace.service.implementation;

import com.uade.tpo.marketplace.entity.Book;
import com.uade.tpo.marketplace.entity.Buy;
import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartBook;
import com.uade.tpo.marketplace.entity.CartMalbum;
import com.uade.tpo.marketplace.entity.MusicAlbum;
import com.uade.tpo.marketplace.exceptions.CartException;
import com.uade.tpo.marketplace.exceptions.ProductException;
import com.uade.tpo.marketplace.repository.CartRepository;
import com.uade.tpo.marketplace.service.BookService;
import com.uade.tpo.marketplace.service.BuyService;
import com.uade.tpo.marketplace.service.CartService;
import com.uade.tpo.marketplace.service.MusicAlbumService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private MusicAlbumService musicAlbumService;
    @Autowired
    private BuyService buyService;
   
    public Cart createCart() throws Exception {
        try {
          Cart cart = new Cart();
          return cart;
        } catch (Exception error) {
          throw new Exception("[CartService.createCart] -> " + error.getMessage());
        }
    }

    public Cart getCartById(Long cartId) throws Exception {
    try {
        return cartRepository.findById(cartId)
          .orElseThrow(() -> new CartException("No se encontro el carro."));
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[CartService.getCartById] -> " + error.getMessage());
        }
    }

    @Transactional
    public CartBook addItemBook(Cart cart, Long bookId) throws Exception {
        try {
            Book book = bookService.getBookById(bookId);
            if (book.getStock() == 0) {
                throw new ProductException("Se acabo el stock del producto seleccionado");
            }
            CartBook cartItem = cart.getBookItems().stream()
                .filter(item -> item.getBookId().equals(book.getId()))
                .findFirst()
                .orElse(null);

            if (cartItem != null) {
                // Si ya existe, actualizamos la cantidad
                if (book.getStock() < cartItem.getQuantityBook() + 1) {
                    throw new ProductException("No hay stock suficiente del producto elegido");
                } else {
                    cartItem.setQuantityBook(cartItem.getQuantityBook() + 1);
                }
            } else {
            // Si no existe, creamos un nuevo ítem
                cartItem = new CartBook();
                cartItem.setBook(book);
                cartItem.setQuantityBook(1);
                cartItem.setCart(cart);
            // Agregamos el nuevo ítem al carrito
                cart.getBookItems().add(cartItem);
            }
            // Guardar el carrito actualizado
            cartRepository.save(cart);
            return cartItem;
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[CartService.addBookToCart] -> " + error.getMessage());
        }
    }
    
    @Transactional
    public CartMalbum addItemMusicAlbum(Cart cart, Long musicAlbumId) throws Exception {
        try {
            MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(musicAlbumId);
            if (musicAlbum.getStock() == 0) {
                throw new ProductException("Se acabo el stock del producto seleccionado");
            }
            CartMalbum cartItem = cart.getMalbumItems().stream()
                .filter(item -> item.getMusicAlbumId().equals(musicAlbum.getId()))
                .findFirst()
                .orElse(null);

            if (cartItem != null) {
            // Si ya existe, actualizamos la cantidad
                if (musicAlbum.getStock() < cartItem.getQuantityMalbum() + 1) {
                    throw new ProductException("No hay stock suficiente del producto elegido");
                } else {
                    cartItem.setQuantityMalbum(cartItem.getQuantityMalbum() + 1);
                }
            } else {
                // Si no existe, creamos un nuevo ítem
                cartItem = new CartMalbum();
                cartItem.setMusicAlbum(musicAlbum);
                cartItem.setQuantityMalbum(1);
                cartItem.setCart(cart);
            // Agregamos el nuevo ítem al carrito
            cart.getMalbumItems().add(cartItem);
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

    @Transactional
    public void removeBookFromCart(Cart cart, Long bookId) throws Exception {
        try {
            Book book = bookService.getBookById(bookId);
            CartBook item = cart.getBookItems().stream()
                .filter(cartItem -> cartItem.getBookId().equals(book.getId()))
                .findFirst()
                .orElse(null);

            if (item != null) {
                if (item.getQuantityBook() == 1) {
                cart.getBookItems().remove(item);
                } else {
                    item.setQuantityBook(item.getQuantityBook() - 1);
                }
            }
            cartRepository.save(cart);
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (Exception error) {
        throw new Exception("[CartService.removeBookFromCart] -> " + error.getMessage());
        }
    }
   
    @Transactional
    public void removeMalbumFromCart(Cart cart, Long malbumId) throws Exception {
        try {
            MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(malbumId);
            CartMalbum item = cart.getMalbumItems().stream()
                .filter(cartItem -> cartItem.getMusicAlbumId().equals(musicAlbum.getId()))
                .findFirst()
                .orElse(null);
            if (item != null) {
                if (item.getQuantityMalbum() == 1) {
                cart.getMalbumItems().remove(item);
                } else {
                    item.setQuantityMalbum(item.getQuantityMalbum() - 1);
                }
            }
            cartRepository.save(cart);
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (Exception error) {
        throw new Exception("[CartService.removeBookFromCart] -> " + error.getMessage());
        }
    }

    @Transactional
    public void removeItemBookFromCart(Long cartId, Long bookId) throws Exception {
      try {
        Cart cart = getCartById(cartId);
        Book book = bookService.getBookById(bookId);
        CartBook item = cart.getBookItems().stream()
            .filter(cartItem -> cartItem.getBookId().equals(book.getId()))
            .findFirst()
            .orElse(null);
        cart.getBookItems().remove(item);
        cartRepository.save(cart);
      } catch (CartException error) {
        throw new CartException(error.getMessage());
      } catch (Exception error) {
        throw new Exception("[CartService.removeItemFromCart] -> " + error.getMessage());
      }
    }

    @Transactional
    public void removeItemMalbumFromCart(Long cartId, Long malbumId) throws Exception {
      try {
        Cart cart = getCartById(cartId);
        MusicAlbum musicAlbum = musicAlbumService.getMusicAlbumById(malbumId);
        CartMalbum item = cart.getMalbumItems().stream()
            .filter(cartItem -> cartItem.getMusicAlbumId().equals(musicAlbum.getId()))
            .findFirst()
            .orElse(null);
        cart.getMalbumItems().remove(item);
        cartRepository.save(cart);
      } catch (CartException error) {
        throw new CartException(error.getMessage());
      } catch (Exception error) {
        throw new Exception("[CartService.removeItemFromCart] -> " + error.getMessage());
      }
    }

    public void emptyCart(Long cartId) throws Exception {
        try {
            Cart cart = getCartById(cartId);
            cart.getBookItems().clear();
            cart.getMalbumItems().clear();
            cartRepository.save(cart);
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (Exception error) {
        throw new Exception("[CartService.emptyCart] -> " + error.getMessage());
        }
    }

    public Buy checkout(Long cartId) throws Exception {
        try {
            Cart cart = getCartById(cartId);
            Buy buy = buyService.createBuy(cart);
        emptyCart(cartId);
        return buy;
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[CartService.checkout] -> " + error.getMessage());
        }
    }

    @Transactional
    public CartBook addItemBookWithQuantity(Cart cart, Long bookId, int quantity) throws Exception {
        try {
            Book book = bookService.getBookById(bookId);
            if (book.getStock() == 0) {
                throw new ProductException("Se acabó el stock del producto seleccionado");
            }
            
            CartBook cartItem = cart.getBookItems().stream()
                .filter(item -> item.getBookId().equals(book.getId()))
                .findFirst()
                .orElse(null);

            if (cartItem != null) {
                // Si ya existe, verificamos que haya stock suficiente para la cantidad total
                if (book.getStock() < cartItem.getQuantityBook() + quantity) {
                    throw new ProductException("No hay stock suficiente del producto elegido");
                } else {
                    cartItem.setQuantityBook(cartItem.getQuantityBook() + quantity);
                }
            } else {
                // Si no existe, verificamos que haya stock suficiente para la cantidad solicitada
                if (book.getStock() < quantity) {
                    throw new ProductException("No hay stock suficiente del producto elegido");
                }
                // Creamos un nuevo ítem
                cartItem = new CartBook();
                cartItem.setBook(book);
                cartItem.setQuantityBook(quantity);
                cartItem.setCart(cart);
                // Agregamos el nuevo ítem al carrito
                cart.getBookItems().add(cartItem);
            }
            // Guardar el carrito actualizado
            cartRepository.save(cart);
            return cartItem;
        } catch (CartException error) {
            throw new CartException(error.getMessage());
        } catch (ProductException error) {
            throw new ProductException(error.getMessage());
        } catch (Exception error) {
            throw new Exception("[CartService.addItemBookWithQuantity] -> " + error.getMessage());
        }
    }
}