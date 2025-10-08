package com.bookstore.backend.controllers;

import com.bookstore.backend.entities.User;
import com.bookstore.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {
    private MockMvc mockMvc;
    private UserService authService;
    private AuthController authController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(UserService.class);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_shouldReturnErrorMessageForInvalidCredentials() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("wrongpassword");
        when(authService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        String jsonContent = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void register_shouldCreateNewUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        when(authService.register(Mockito.any())).thenReturn(user);
        String jsonContent = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }
}
