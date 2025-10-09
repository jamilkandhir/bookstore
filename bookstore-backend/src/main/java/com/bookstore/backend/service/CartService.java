package com.bookstore.backend.service;

import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.exception.CustomException;
import com.bookstore.backend.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final CartItemRepository cartItemRepository;

    public ResponseEntity<List<CartItem>> getAllCartItems() {
        try {
            logger.info("Fetching all cart items");
            List<CartItem> items = cartItemRepository.findAll();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all cart items", ex);
            throw new CustomException("Failed to fetch cart items", ex);
        }
    }

    public ResponseEntity<CartItem> addToCart(CartItem item) {
        try {
            logger.info("Adding item to cart: {}", item);
            CartItem savedItem = cartItemRepository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error occurred while adding item to cart: {}", item, ex);
            throw new CustomException("Failed to add item to cart", ex);
        }
    }

    public ResponseEntity<Void> removeFromCart(Long id) {
        try {
            logger.info("Removing item from cart by ID: {}", id);
            if (cartItemRepository.existsById(id)) {
                cartItemRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.error("Error occurred while removing item from cart by ID: {}", id, ex);
            throw new CustomException("Failed to remove item from cart", ex);
        }
    }

    public ResponseEntity<CartItem> getCartItemById(Long id) {
        try {
            logger.info("Fetching cart item by ID: {}", id);
            Optional<CartItem> item = cartItemRepository.findById(id);
            return item.map(cartItem -> new ResponseEntity<>(cartItem, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            logger.error("Error occurred while fetching cart item by ID: {}", id, ex);
            throw new CustomException("Failed to fetch cart item", ex);
        }
    }
}
