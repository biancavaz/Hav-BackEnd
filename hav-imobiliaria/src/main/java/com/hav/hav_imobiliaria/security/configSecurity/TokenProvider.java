package com.hav.hav_imobiliaria.security.configSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
<<<<<<< Updated upstream
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
=======
//import org.springframework.security.core.Authentication;
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {

        return Jwts.builder().setIssuer("HAV")
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", authentication.getName())
                .claim("role", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .signWith(key)
                .compact();
    }
    public String generatePasswordResetToken(Integer userId) {
        return Jwts.builder()
                .setIssuer("HAV")
                .setSubject("PasswordReset")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutos
                .claim("userId", userId)
                .signWith(key)
                .compact();
    }
    public Integer getUserIdFromPasswordResetToken(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("userId", Integer.class);
    }

    public String getEmailFromToken(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return String.valueOf(claims.get("email"));
    }
}
