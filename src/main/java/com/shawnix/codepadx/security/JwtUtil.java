package com.shawnix.codepadx.security;

import com.shawnix.codepadx.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.access_token_secret}")
    private String accessTokenSecret;

    @Value("${jwt.access_token_expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refresh_token_secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh_token_expiration}")
    private int refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Long userId = ((User) userDetails).getId();

        claims.put("userId", userId);
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getAccesTokenSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getRefreshTokenSigningKey())
                .compact();
    }
    public Long extractUserIdWithAccessToken(String accessToken) {
        Object userId = Jwts.parser()
                .verifyWith(getAccesTokenSigningKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .get("userId");
        return ((Number) userId).longValue();
    }

    public Long extractUserIdWithRefreshToken(String refreshToken) {
        Object userId = Jwts.parser()
                .verifyWith(getRefreshTokenSigningKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .get("userId");
        return ((Number) userId).longValue();
    }



    private SecretKey getRefreshTokenSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private SecretKey getAccesTokenSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean validateAccesTokenToken(String accessToken, UserDetails userDetails) {
        Long tokenUserId = extractUserIdWithAccessToken(accessToken);
        Long actualUserId = ((User) userDetails).getId();
        return tokenUserId.equals(actualUserId) && !isAccessTokenTokenExpired(accessToken);
    }

    public boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        Long userId = extractUserIdWithRefreshToken(refreshToken);
        Long actualUserId = ((User) userDetails).getId();
        return userId.equals(actualUserId) && !isRefreshTokenTokenExpired(refreshToken);
    }

    private boolean isAccessTokenTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getAccesTokenSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    private boolean isRefreshTokenTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(getRefreshTokenSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }


}
