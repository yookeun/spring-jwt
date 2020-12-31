package com.example.springjwt.util;

import com.example.springjwt.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String secretKey;


    private String createToken(Map<String, Object> claims) {
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .signWith(key)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .compact();

    }

    private Claims extractAllClaims(String token) {
        if (StringUtils.isEmpty(token)) return null;
        String secretKeyEncodeBase64 = Encoders.BASE64.encode(secretKey.getBytes());
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secretKeyEncodeBase64).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            claims = null;
        }
        return claims;
    }



    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        if (claims == null) return null;
        else return claims.get("username",String.class);
    }

    public String generateToken(Users users) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", users.getUserId());
        return createToken(claims);
    }
}
