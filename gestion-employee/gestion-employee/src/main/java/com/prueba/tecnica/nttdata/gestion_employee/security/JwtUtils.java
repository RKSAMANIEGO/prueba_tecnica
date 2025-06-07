package com.prueba.tecnica.nttdata.gestion_employee.security;
import java.security.Key;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    final String SECRET_KEY = "enrikerodkelersamaniegoguzmanenrikerodkelersamaniegoguzmanenrikerodkelersamaniegoguzmanenrikerodkelersamaniegoguzman";
    
    public String createToken(UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24 ))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean tokenIsValid(String token, UserDetails user){
        return extractUsername(token).equals(user.getUsername()) && !isExpiredToken(token);
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    private Boolean isExpiredToken(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims (String token){
        return  Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
 
    private Key key(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}
