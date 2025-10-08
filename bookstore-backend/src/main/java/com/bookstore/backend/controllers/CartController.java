package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.CartItem;
import com.bookstore.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

private final CartService cartService;



    @GetMapping
    public List<CartItem> getCartItems() {
        return cartService.getAllCartItems();
    }
    @GetMapping("/{id}")
    public CartItem getCartItemById(@PathVariable Long id) {
        return cartService.getCartItemById(id).orElse(null);
    }

    @PostMapping
    public CartItem addToCart(@RequestBody CartItem item) {
        return cartService.addToCart(item);
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }
}
