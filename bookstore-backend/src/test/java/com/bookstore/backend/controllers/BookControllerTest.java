package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class BookControllerTest {

    private MockMvc mockMvc;
    private BookService bookService;
    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookService = Mockito.mock(BookService.class);
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void getAllBooks_shouldReturnJsonArray() throws Exception {
        Book book = new Book(1L, "Test Book", "Jamil Ahmed", 39.99);
        when(bookService.getAllBooks()).thenReturn(List.of(book));
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

}