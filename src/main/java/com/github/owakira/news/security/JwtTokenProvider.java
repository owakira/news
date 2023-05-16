package com.github.owakira.news.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    private final Long jwtExpiresIn;
    private final Key secretKey;

    public JwtTokenProvider(
            UserDetailsService userService,
            @Value("${security.jwt-expires-in}") Long jwtExpiresIn,
            @Value("${security.jwt-secret-key}") String jwtSecretKey
    ) {
        this.userDetailsService = userService;
        this.jwtExpiresIn = jwtExpiresIn;
        var keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiresIn))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            var claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn(ex);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        var user = userDetailsService.loadUserByUsername(getUsernameByAccessToken(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    private String getUsernameByAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
