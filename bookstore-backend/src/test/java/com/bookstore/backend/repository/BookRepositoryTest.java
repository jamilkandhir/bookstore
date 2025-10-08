package com.bookstore.backend.repository;

import com.bookstore.backend.entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void saveAndFindAll_shouldReturnBooks() {
        Book book1 = new Book(null, "Book One", "Author One", 10.99);
        Book book2 = new Book(null, "Book Two", "Author Two", 15.99);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).contains("Book One", "Book Two");
    }
}
