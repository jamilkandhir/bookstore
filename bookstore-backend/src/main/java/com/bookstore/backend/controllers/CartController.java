package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        logger.info("Fetching all cart items");
        return cartService.getAllCartItems();
    }
    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem item) {
        logger.info("Adding item to cart: {}", item);
        return cartService.addToCart(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        logger.info("Removing item from cart with ID: {}", id);
        ResponseEntity<CartItem> item = cartService.getCartItemById(id);
        if (item.getStatusCode() == HttpStatus.OK) {
            cartService.removeFromCart(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        logger.info("Fetching cart item with ID: {}", id);
        return cartService.getCartItemById(id);
    }
}
