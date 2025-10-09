package com.bookstore.backend.service;

import com.bookstore.backend.entities.User;
import com.bookstore.backend.exception.CustomException;
import com.bookstore.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        try {
            logger.info("Registering user: {}", user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception ex) {
            logger.error("Error occurred while registering user: {}", user.getUsername(), ex);
            throw new CustomException("Failed to register user", ex);
        }
    }

    public boolean login(String username, String password) {
        try {
            logger.info("Attempting login for user: {}", username);
            Optional<User> userOpt = userRepository.findByUsername(username);
            boolean isValid = userOpt.isPresent() && userOpt.get().getPassword().equals(password);
            logger.info("Login {} for user: {}", isValid ? "successful" : "failed", username);
            return isValid;
        } catch (Exception ex) {
            logger.error("Error occurred during login for user: {}", username, ex);
            throw new CustomException("Failed to login", ex);
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            logger.info("Finding user by username: {}", username);
            return userRepository.findByUsername(username);
        } catch (Exception ex) {
            logger.error("Error occurred while finding user: {}", username, ex);
            throw new CustomException("Failed to find user", ex);
        }
    }
}