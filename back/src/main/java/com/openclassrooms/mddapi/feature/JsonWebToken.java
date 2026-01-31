package com.openclassrooms.mddapi.feature;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JsonWebToken {

    private final String SECRET_KEY_STRING;
    private final SecretKey SECRET_KEY;

    public JsonWebToken(String secretKeyString) {
        this.SECRET_KEY_STRING = secretKeyString;
        SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, long tokenExpiration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("sub", username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(tokenExpiration, ChronoUnit.MILLIS)))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean isSignatureValid(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaimsFromToken(token);

            return isSignatureValid(token) &&
                    !isTokenExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}