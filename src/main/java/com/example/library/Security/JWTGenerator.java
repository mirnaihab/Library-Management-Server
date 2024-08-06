package com.example.library.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {
    private final Key key = new SecretKeySpec("ThisIsMySecretKey123456789101112".getBytes(), "HmacSHA256");

    public String generateToken(Authentication authentication) {

        Date currentDate = new Date();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Claims claims = Jwts.claims().add("Authorities",userPrincipal.getAuthorities())
                .add("Username",userPrincipal.getUsername()).build();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }
    public String getUsernameFromJWT(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getBody();
            return claims.getSubject();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }
}
