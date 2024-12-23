package com.moviex.service;

import com.moviex.config.JwtConfigurationProperties;
import com.moviex.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperties;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApplicationScoped
public class JwtService {
    
    @Inject
    @ConfigProperties(prefix = "jwt")
    private JwtConfigurationProperties jwtConfigurationProperties;
    
    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigurationProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(jwtConfigurationProperties.getExpirationTime())))
                .signWith(this.getSigningKey())
                .compact();
    }

    public String generateRememberToken(UserDto userDto){
        return generateToken(new HashMap<>() , userDto.getEmail());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractSubject(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String subject){
        return extractSubject(token).equals(subject) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date now = Date.from(Instant.now());
        return extractExpiration(token).before(now);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
