package com.sttivenquiroga.products.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.date}")
    private long dateExpiration;

    public String generateToken(String username) {
        return getToken(new HashMap<>(), username);
    }

    private String getToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder().claims(extraClaims).subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + dateExpiration))
                .signWith(getKey(),Jwts.SIG.HS256).compact();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parser().verifyWith(getKey()).build().parse(token).getPayload();
            return true;
        } catch (Exception e) {
            LOGGER.info("JwtService, token invalid: {}", e.getMessage());
            return false;
        }
    }

    public String getUserName(String token){
        return getClaim(token,Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractClaims(String token){
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    SecretKey getTestKey(){
        return this.getKey();
    }
}
