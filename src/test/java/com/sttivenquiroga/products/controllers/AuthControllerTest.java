package com.sttivenquiroga.products.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sttivenquiroga.products.dto.AuthResponse;
import com.sttivenquiroga.products.dto.LoginRequest;
import com.sttivenquiroga.products.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void login() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn(new AuthResponse("1234567890"));
        LoginRequest loginRequest = new LoginRequest("user", "12345");
        ObjectMapper objectMapper = new ObjectMapper();
        String loginInfo = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("1234567890"));
    }
}
