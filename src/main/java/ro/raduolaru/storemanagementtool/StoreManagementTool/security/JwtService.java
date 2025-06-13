package ro.raduolaru.storemanagementtool.StoreManagementTool.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long EXPIRATION_MILLIS;

    public String getUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
            .signWith(getEncodedSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        return userDetails.getUsername().equals(getUsername(token)) && !getExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = Jwts.parserBuilder().setSigningKey(getEncodedSigningKey(SECRET_KEY)).build().parseClaimsJws(token).getBody();
        return resolver.apply(claims);
    }

    private Key getEncodedSigningKey(String key){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }
}
