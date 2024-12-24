package ua.ihor.SpringBootSecurity4.usersDetails;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;
    private Key secretKey;

    @PostConstruct
    private void init(){
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + 60 * 60 * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setSubject("User details")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setIssuer("Ihor")
                .signWith(secretKey)
                .compact();
    }

    public String validateTokenAndRetrieveClaim(String token) throws JwtException {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey) // Use a Key instance
                .build()
                .parseClaimsJws(token);

        String subject = claimsJws.getBody().getSubject();
        Date issuedAt = claimsJws.getBody().getIssuedAt();
        Date expiration = claimsJws.getBody().getExpiration();
        String issuer = claimsJws.getBody().getIssuer();

        return claimsJws.getBody().get("username").toString();
    }
}
