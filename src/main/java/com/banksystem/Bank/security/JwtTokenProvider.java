package com.banksystem.Bank.security;


import com.banksystem.Bank.dto.securityUser;
import com.banksystem.Bank.entity.Bank;
import com.banksystem.Bank.exciption.BankApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private  String SECRET_KEY;
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;
    public String extractUsername(String token) {
        return extractUserName(token);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(securityUser securityUser){
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", securityUser.getId());
        claims.put("bankId", securityUser.getBank().getId());
        claims.put("username", securityUser.getUsername());
        return generateToken(claims,securityUser);
    }

    public String generateToken(Map<String, Object> extraClaims, securityUser securityUser){
        return Jwts
                .builder()
                .setSubject(securityUser.getUsername())
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationDate))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public boolean isTokenValid(String token , UserDetails userDetails){
        final String tokenUsername=extractUsername(token);
        return (tokenUsername.equals(userDetails.getUsername()) && !isTokeExpired(token));
    }

    private boolean isTokeExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public int extractUserId(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("userId", Integer.class);
    }

    public String extractUserName(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("username", String.class);
    }

    public Integer extractBankId(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("bankId", Integer.class);
    }

}
