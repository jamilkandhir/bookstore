package com.bookstore.backend.service;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTest {

    @Test
    void getAllBooks_shouldReturnBooks() {

        BookRepository bookRepo = Mockito.mock(BookRepository.class);
        BookService bookService = new BookService(bookRepo);

        Book book1 = new Book(1L, "Core Java", "Jamil Ahmed", 29.99);
        Mockito.when(bookRepo.findAll()).thenReturn(List.of(book1));

        List<Book> books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Core Java", books.get(0).getTitle());
    }
}