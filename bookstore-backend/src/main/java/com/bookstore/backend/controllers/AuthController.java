package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.User;
import com.bookstore.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> existing = userService.findByUsername(user.getUsername());
        if(existing.isPresent() && existing.get().getPassword().equals(user.getPassword())) {
            return "Login successful!";
        }
        return "Invalid credentials";
    }
    @PostMapping("/getUserByUsername")
    public Optional<User> findByUsername(@RequestBody String username) {
        return userService.findByUsername(username);
    }
}