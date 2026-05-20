package com.edunest.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JwtUtils
 * ─────────────────────────────────────────────────────────────
 * Path:  src/main/java/com/edunest/security/JwtUtils.java
 *
 * Responsibilities:
 *   1. Generate JWT token after successful login
 *   2. Extract userId (subject) from a token
 *   3. Validate a token (signature + expiry)
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // ── Build signing key from secret ─────────────────────────────────
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(
            java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes())
        );
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ── Generate token — stores userId as the subject ─────────────────
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // ── Extract userId from token ─────────────────────────────────────
    public String getUserIdFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // ── Validate token — returns true if valid ────────────────────────
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());

        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());

        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());

        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    // ── Check token belongs to this user ─────────────────────────────
    public boolean validateToken(String token, UserDetails userDetails) {
        String userId = getUserIdFromToken(token);
        return userId.equals(userDetails.getUsername()) && validateToken(token);
    }
}