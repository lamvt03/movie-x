package com.filmweb.utils;

import com.filmweb.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ApplicationScoped
public class JwtUtils {

    @Inject
    @ConfigProperty(name = "jwt.secret")
    private String secretKey;

    @Inject
    @ConfigProperty(name = "jwt.expirationTime")
    private Long expirationTime;

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(expirationTime)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRememberToken(UserDto userDto){
        return generateToken(new HashMap<>() , userDto.getEmail());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
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
