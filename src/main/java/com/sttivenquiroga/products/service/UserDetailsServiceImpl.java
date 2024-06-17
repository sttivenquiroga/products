package com.sttivenquiroga.products.service;

import com.sttivenquiroga.products.entity.UserEntity;
import com.sttivenquiroga.products.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityDetails = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(userEntityDetails.getUsername(), userEntityDetails.getPassword(),
                true, true, true, true,
                userEntityDetails.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
                        .collect(Collectors.toSet()));
    }
}
