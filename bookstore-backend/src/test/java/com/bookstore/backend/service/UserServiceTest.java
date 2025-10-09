package com.bookstore.backend.service;

import com.bookstore.backend.entities.User;
import com.bookstore.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void register_shouldSaveUserSuccessfully() {
        User user = new User(null, "testuser", "password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User result = userService.register(user);

        assertEquals(user, result);
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
    }

   // @Test
    void login_shouldReturnTrueForValidCredentials() {
        User user = new User(1L, "jamil", "1234");
        when(userRepository.findByUsername("jamil")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encodedPassword")).thenReturn(true);

        boolean result = userService.login("jamil", "1234");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("jamil");
        verify(passwordEncoder, times(1)).matches("1234", "encodedPassword");
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
