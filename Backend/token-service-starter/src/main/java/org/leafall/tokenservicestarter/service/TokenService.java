package org.leafall.tokenservicestarter.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class TokenService {

    private final SecretKey secretKey;

    public TokenService(String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
