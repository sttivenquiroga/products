package com.sttivenquiroga.products.service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private Decoders decoders;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.date}")
    private long dateExpiration;

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtService, "dateExpiration", dateExpiration);
    }

    @Test
    public void generateToken(){
        SecretKey key = jwtService.getTestKey();
        String token = jwtService.generateToken("juan");
        Assert.isTrue(key instanceof SecretKey);
        Assert.hasText(token);
        Assert.isTrue(jwtService.isTokenValid(token));
        Assert.eq("juan", jwtService.getUserName(token), "equals");
    }

    @Test
    public void isInvalidToken(){
        ReflectionTestUtils.setField(jwtService, "dateExpiration", 0);
        SecretKey key = jwtService.getTestKey();
        String token = jwtService.generateToken("juan");
        Assert.isTrue(key instanceof SecretKey);
        Assert.hasText(token);
        Assert.isTrue(!jwtService.isTokenValid(token));
    }
}
