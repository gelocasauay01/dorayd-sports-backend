package com.dorayd.sports.features.auth.services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService{

    // TODO: Move to application.yaml
    private static final String SECRET_KEY = "f79008cdcb8f6f4c059c248752ff64c97fe5a7a3fe50dd37f1aa2f4b023fa26e";


    @Override
    public String generateToken(UserDetails userDetails, Date issueDate, Date expirationDate) {
        String token = Jwts
            .builder()
            .subject(userDetails.getUsername())
            .issuedAt(issueDate)
            .expiration(expirationDate)
            .signWith(getSignInKey())
            .compact();
        log.info("JWT generated: {}", token);
        return token;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername());
        } catch(ExpiredJwtException e) {
            log.warn("JWT is expired: {}", e.getMessage());
            return false;
        }
        
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
