package com.bookstore.backend.service;

import com.bookstore.backend.entities.Book;
import com.bookstore.backend.exception.CustomException;
import com.bookstore.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        try {
            logger.info("Fetching all books");
            return bookRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all books", ex);
            throw new CustomException("Failed to fetch books", ex);
        }
    }

    public Optional<Book> getBookById(Long id) {
        try {
            logger.info("Fetching book by ID: {}", id);
            return bookRepository.findById(id);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching book by ID: {}", id, ex);
            throw new CustomException("Failed to fetch book", ex);
        }
    }

    public Book saveBook(Book book) {
        try {
            logger.info("Saving book: {}", book.getTitle());
            return bookRepository.save(book);
        } catch (Exception ex) {
            logger.error("Error occurred while saving book: {}", book.getTitle(), ex);
            throw new CustomException("Failed to save book", ex);
        }
    }

    public void deleteBook(Long id) {
        try {
            logger.info("Deleting book by ID: {}", id);
            bookRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Error occurred while deleting book by ID: {}", id, ex);
            throw new CustomException("Failed to delete book", ex);
        }
    }
}