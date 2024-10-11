package com.sudzey.sudzey.service.Impl;


import com.sudzey.sudzey.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${sudzey.app.jwtSecret}")
    private String secretKey;

    private Key signingKey;

    @PostConstruct
    private void init() {
        // Initialize the signing key using the secret key
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generate token
    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Token valid for 24 minutes
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Extract claims from token
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get expiration date from token
    @Override
    public Date getExpirationTime(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationTime(token);
        return expiration.before(new Date());
    }

    // Validate token
    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}