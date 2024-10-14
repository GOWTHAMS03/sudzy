package com.sudzey.sudzey.service.Impl;


import com.sudzey.sudzey.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${sudzey.app.jwtSecret}")
    private String secretKey;

    private Key signingKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);


    @PostConstruct
    private void init() {
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generate token
    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Collect and add roles to the claims
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        long expirationTimeMillis = 60L * 24 * 60 * 60 * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeMillis);

        logger.info("Generating token for user: {}", userDetails.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
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
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
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
        final Claims claims = extractAllClaims(token);

        // Extract roles from token
        List<String> roles = (List<String>) claims.get("roles");

        // Ensure roles and username match
        logger.info("Validating token for user: {} with roles: {}", username, roles);

        // Check if username matches and token is not expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}