package com.bookstore.backend.service;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    private CartService cartService;
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        cartItemRepository = Mockito.mock(CartItemRepository.class);
        cartService = new CartService(cartItemRepository);
    }

    @Test
    void addToCart_shouldAddItemSuccessfully() {
        Book book = new Book(1L, "Book Title", "Author Name", 19.99);
        CartItem cartItem = new CartItem(1L, book, 2);

        when(cartItemRepository.save(Mockito.any(CartItem.class))).thenReturn(cartItem);

        CartItem result = cartService.addToCart(cartItem);

        assertEquals(cartItem, result);
        verify(cartItemRepository, times(1)).save(cartItem);
    }

    @Test
    void getAllCartItems_shouldReturnAllItems() {
        Book book = new Book(1L, "Book Title", "Author Name", 19.99);
        CartItem cartItem = new CartItem(1L, book, 2);

        when(cartItemRepository.findAll()).thenReturn(List.of(cartItem));

        List<CartItem> result = cartService.getAllCartItems();

        assertEquals(1, result.size());
        assertEquals(cartItem, result.get(0));
        verify(cartItemRepository, times(1)).findAll();
    }

    @Test
    void removeFromCart_shouldRemoveItemById() {
        Long cartItemId = 1L;

        doNothing().when(cartItemRepository).deleteById(cartItemId);

        cartService.removeFromCart(cartItemId);

        verify(cartItemRepository, times(1)).deleteById(cartItemId);
    }
}
