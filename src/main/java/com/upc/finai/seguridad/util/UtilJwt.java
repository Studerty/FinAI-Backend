package com.upc.finai.seguridad.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class UtilJwt {

    // IMPORTANTE: La clave debe tener al menos 32 caracteres para HS256
    private final String SECRET_KEY = "1c33dd0378e251a32711a589cb539376ed0a190cd4abe83427766624c5603624bc82da96d63b16ea7df73793ee1121be05ad3bd32a2ef70b419a71939b6ae950";

    private SecretKey obtenerLlave() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String extraerCorreo(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerTodosLosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extraerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerLlave())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generarToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return crearToken(claims, userDetails.getUsername());
    }

    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(obtenerLlave(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validarToken(String token, UserDetails userDetails) {
        final String correo = extraerCorreo(token);
        return (correo.equals(userDetails.getUsername()) && !estaExpirado(token));
    }

    private Boolean estaExpirado(String token) {
        return extraerClaim(token, Claims::getExpiration).before(new Date());
    }
}