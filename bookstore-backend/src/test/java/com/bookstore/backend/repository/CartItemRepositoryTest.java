package com.bookstore.backend.repository;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.entities.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

   // @Test
    void saveAndFindAll_shouldReturnCartItems() {
        Book book = new Book(null, "Book Title", "Author Name", 19.99);
        bookRepository.save(book);

        CartItem cartItem = new CartItem(null, book, 2);
        cartItemRepository.save(cartItem);

        List<CartItem> cartItems = cartItemRepository.findAll();

        assertThat(cartItems).hasSize(1);
        assertThat(cartItems.get(0).getBook().getTitle()).isEqualTo("Book Title");
    }
}
