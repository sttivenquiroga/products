package com.sttivenquiroga.products.service;

import com.sttivenquiroga.products.dto.AuthResponse;
import com.sttivenquiroga.products.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserDetailsServiceImpl userDetailsService;
    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserDetailsServiceImpl userDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        return AuthResponse.builder().token(jwtService.generateToken(user.getUsername())).build();
    }
}
