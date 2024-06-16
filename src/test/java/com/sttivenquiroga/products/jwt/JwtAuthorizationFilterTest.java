package com.sttivenquiroga.products.jwt;

import com.sttivenquiroga.products.service.JwtService;
import com.sttivenquiroga.products.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class JwtAuthorizationFilterTest {

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private HttpServletResponse response;

    private HttpServletRequest request;

    private FilterChain filterChain;

    private Authentication authenticationResult;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        authenticationManager = mock(AuthenticationManager.class);
        authenticationResult = mock(Authentication.class);
    }

    @Test
    public void internalFilterTest() throws ServletException, IOException {
        String authHeader = "Bearer 123456789";
        UserDetails userDetails = mock(UserDetails.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(authHeader);
        when(jwtService.isTokenValid("123456789")).thenReturn(true);
        when(jwtService.getUserName("123456789")).thenReturn("juan");
        when(userDetailsService.loadUserByUsername("juan")).thenReturn(userDetails);
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
    }
}
