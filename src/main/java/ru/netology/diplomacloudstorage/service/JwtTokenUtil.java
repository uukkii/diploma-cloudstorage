package ru.netology.diplomacloudstorage.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.diplomacloudstorage.model.User;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenUtil {
    private static final String BASE64_CODE = "VGhpcyBpcyBuZXcgdG9rZW4gZm9yIGRpcGxvbWEh";
    private static final String USERNAME_GET_REQUEST = "username";
    private static final Key KEY = new SecretKeySpec(Base64.getDecoder().decode(BASE64_CODE), SignatureAlgorithm.HS256.getJcaName());
    private static final int TOKEN_EXPIRATION_PERIOD = 30*60*1000;


    public boolean validate(String token) {
        try {
            parseToken(token);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
           e.getMessage();
        }
        return false;
    }

    public String getUsername(String token) {
        return (String) parseToken(token).getBody().get(USERNAME_GET_REQUEST);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claim(USERNAME_GET_REQUEST, user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_PERIOD))
                .signWith(KEY)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token);
    }
}
