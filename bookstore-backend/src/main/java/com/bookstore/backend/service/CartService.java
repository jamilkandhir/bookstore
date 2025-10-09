package com.bookstore.backend.service;

import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.exception.CustomException;
import com.bookstore.backend.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        try {
            logger.info("Fetching all cart items");
            return cartItemRepository.findAll();
        } catch (Exception ex) {
            logger.error("Error occurred while fetching all cart items", ex);
            throw new CustomException("Failed to fetch cart items", ex);
        }
    }

    public CartItem addToCart(CartItem item) {
        try {
            logger.info("Adding item to cart: {}", item);
            return cartItemRepository.save(item);
        } catch (Exception ex) {
            logger.error("Error occurred while adding item to cart: {}", item, ex);
            throw new CustomException("Failed to add item to cart", ex);
        }
    }

    public void removeFromCart(Long id) {
        try {
            logger.info("Removing item from cart by ID: {}", id);
            cartItemRepository.deleteById(id);
        } catch (Exception ex) {
            logger.error("Error occurred while removing item from cart by ID: {}", id, ex);
            throw new CustomException("Failed to remove item from cart", ex);
        }
    }

    public Optional<CartItem> getCartItemById(Long id) {
        try {
            logger.info("Fetching cart item by ID: {}", id);
            return cartItemRepository.findById(id);
        } catch (Exception ex) {
            logger.error("Error occurred while fetching cart item by ID: {}", id, ex);
            throw new CustomException("Failed to fetch cart item", ex);
        }
    }
}
