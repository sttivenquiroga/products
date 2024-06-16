package com.sttivenquiroga.products.jwt;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sttivenquiroga.products.entity.UserEntity;
import com.sttivenquiroga.products.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.CoyoteInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private HttpServletResponse response;

    private HttpServletRequest request;

    private FilterChain filterChain;

    private Authentication authenticationResult;

    @BeforeEach
    public void setUp(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        authenticationResult = mock(Authentication.class);
    }

    @Test
    public void testAuthentication() throws ServletException, IOException {
        String token = "tokenDePrueba";
        UserDetails userDetails = mock(UserDetails.class);
        User user = mock(User.class);
        UserEntity userEntity = new UserEntity(1, "user", "juan", "perez", "12345", new HashSet<>());
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = objectMapper.writeValueAsBytes(userEntity);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setContent(jsonData);
        when(request.getInputStream()).thenReturn(mockHttpServletRequest.getInputStream());
        when(authenticationManager.authenticate(any())).thenReturn(authenticationResult);
        when(authenticationResult.getPrincipal()).thenReturn(user);
        when(jwtService.generateToken(authenticationResult.getName())).thenReturn(token);

        jwtAuthenticationFilter.attemptAuthentication(request, response);
        jwtAuthenticationFilter.successfulAuthentication(request, response, filterChain, authenticationResult);
    }
}
