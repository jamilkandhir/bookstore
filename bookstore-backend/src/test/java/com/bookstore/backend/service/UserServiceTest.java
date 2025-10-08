package com.bookstore.backend.service;

import com.bookstore.backend.entities.User;
import com.bookstore.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void register_shouldSaveUserSuccessfully() {
        User user = new User(null, "testuser", "password");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User result = userService.register(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void login_shouldReturnTrueForValidCredentials() {
        User user = new User(1L, "testuser", "password");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        boolean result = userService.login("testuser", "password");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void login_shouldReturnFalseForInvalidCredentials() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        boolean result = userService.login("testuser", "wrongpassword");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void findByUsername_shouldReturnUserWhenExists() {

        User user = new User(1L, "testuser", "password");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> result = userService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void findByUsername_shouldReturnEmptyWhenNotExists() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("nonexistent");

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }
}
