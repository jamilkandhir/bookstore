package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartControllerTest {
    private MockMvc mockMvc;
    private CartService cartService;
    private CartController cartController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        cartService = Mockito.mock(CartService.class);
        cartController = new CartController(cartService);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void addItemToCart_shouldAddItemSuccessfully() throws Exception {
        Book book = new Book(1L, "Book Title", "Author Name", 19.99);
        CartItem cartItem = new CartItem(1L, book, 2);

        when(cartService.addToCart(Mockito.any())).thenReturn(cartItem);
        CartItem requestPayload = new CartItem(1L, book, 2);
        String jsonContent = objectMapper.writeValueAsString(requestPayload);
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Book Title"))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void getAllCartItems_shouldReturnCartItems() throws Exception {
        Book book = new Book(1L, "Book Title", "Author Name", 19.99);

        // Create a CartItem with the mocked Book
        CartItem cartItem = new CartItem(1L, book, 2);

        when(cartService.getAllCartItems()).thenReturn(List.of(cartItem));

        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].book.title").value("Book Title"))
                .andExpect(jsonPath("$[0].quantity").value(2));
    }
}
