package com.sttivenquiroga.products.service;

import com.sttivenquiroga.products.entity.ERole;
import com.sttivenquiroga.products.entity.Role;
import com.sttivenquiroga.products.entity.UserEntity;
import com.sttivenquiroga.products.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Test
    public void loadUserByName(){
        UserEntity userEntity = getUserEntity();
        userEntity.getRoles().add(new Role(1, ERole.ADMIN));
        Mockito.when(userRepository.findByUsername("juan")).thenReturn(Optional.of(userEntity));
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("juan");
        Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(userEntity.getPassword(), userDetails.getPassword());
    }

    private UserEntity getUserEntity(){
        return UserEntity.builder()
                .firstname("juan")
                .id(1)
                .lastname("perez")
                .username("juan")
                .password("12345")
                .roles(new HashSet<>())
                .build();
    }
}
