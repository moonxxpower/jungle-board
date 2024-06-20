package com.example.jungleboard.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {
    public static String createToken(String username, String secretKey, Long expiredTime) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))    // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredTime))    // 토큰 유효 시간
                .signWith(key)
                .compact();
    }

    public static boolean isExpired(String token, String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public static String getUsername(String token, String secretKey) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }
}
