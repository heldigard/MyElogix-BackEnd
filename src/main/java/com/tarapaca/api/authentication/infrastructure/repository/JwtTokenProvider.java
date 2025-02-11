package com.tarapaca.api.authentication.infrastructure.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tarapaca.api.authentication.config.JwtProperties;
import com.tarapaca.api.authentication.domain.model.UserDetailsImpl;
import com.tarapaca.api.authentication.infrastructure.exception.JwtTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private SecretKey signingKey;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Token Generation Methods
    public String generateToken(UserDetailsImpl userDetails) {
        return buildToken(getExtraClaims(userDetails), userDetails, jwtProperties.getExpiration());
    }

    // Método actualizado para validación de tokens de acceso
    public boolean validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .clockSkewSeconds(jwtProperties.getClockSkew()) // Add clock skew
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(cleanToken(token))
                    .getPayload();

            String tokenType = claims.get("tokenType", String.class);
            if (!"ACCESS".equals(tokenType)) {
                log.error("Invalid token type: {}", tokenType);
                return false;
            }

            return !isTokenExpired(claims);
        } catch (Exception e) {
            log.error("Access token validation failed: {}", e.getMessage());
            return false;
        }
    }

    // Método para generar refresh tokens con claims específicos
    public String generateRefreshToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "REFRESH");
        claims.put("userId", userDetails.getId());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(signingKey)
                .compact();
    }

    // Método específico para validar refresh tokens
    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .clockSkewSeconds(jwtProperties.getClockSkew()) // Apply clock skew
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(cleanToken(token))
                    .getPayload();

            String tokenType = claims.get("tokenType", String.class);
            if (!"REFRESH".equals(tokenType)) {
                log.error("Invalid token type: {}", tokenType);
                return false;
            }

            return !isTokenExpired(claims);
        } catch (Exception e) {
            log.error("Refresh token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date(System.currentTimeMillis() - (jwtProperties.getClockSkew() * 1000)));
    }

    // Add getter for clock skew
    public Long getAllowedClockSkewSeconds() {
        return Long.valueOf(jwtProperties.getClockSkew());
    }

    private String cleanToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        extraClaims.put("tokenType", "ACCESS"); // Add token type
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey)
                .compact();
    }

    private Map<String, Object> getExtraClaims(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        claims.put("isActive", userDetails.isEnabled());
        claims.put("isLocked", !userDetails.isAccountNonLocked());
        claims.put("isDeleted", userDetails.isDeleted());
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return claims;
    }

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get("tokenType", String.class);

            // Log token validation attempt
            log.debug("Validating {} token", tokenType);

            // Allow both ACCESS and REFRESH tokens
            if (!"ACCESS".equals(tokenType) && !"REFRESH".equals(tokenType)) {
                log.error("Invalid token type: {}", tokenType);
                throw new JwtTokenException(token, "Invalid token type");
            }

            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            throw new JwtTokenException(token, "Token validation failed");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .clockSkewSeconds(jwtProperties.getClockSkew())
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(cleanToken(token))
                .getPayload();
    }
}
