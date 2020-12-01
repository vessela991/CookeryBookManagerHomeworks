package fmi.springboot.vpopova.recipes.util;

import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import fmi.springboot.vpopova.recipes.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {

    public static final String SECRET = "SECRETSECRETSECRETSECRETSECRETSECRET";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private static Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserPrincipal userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("userId", userDetails.getId());
        claims.put("role", userDetails.getRole());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        return createToken(claims);
    }

    private String createToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Boolean validateToken(String token, UserPrincipal userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUserId(String token) {
        return decodeJWT(token).get("userId").toString();
    }
}