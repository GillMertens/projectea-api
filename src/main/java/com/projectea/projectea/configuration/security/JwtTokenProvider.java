package com.projectea.projectea.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenProvider {

    private final SecretKey signingKey;

    public JwtTokenProvider(@Value("${jwt.signing.key}") String key) {
        byte[] bytes = Decoders.BASE64.decode(key);
        this.signingKey = Keys.hmacShaKeyFor(bytes);
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using a provided resolver function.
     * 
     * @param token The JWT token to extract claims from
     * @param resolver Function to extract the specific claim from Claims object
     * @param <T> The type of the claim to extract
     * @return The extracted claim value
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> resolver) {
        return resolver.apply(getAllClaimsFromToken(token));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with custom claims and user details.
     * Token expires after 10 hours from creation.
     * 
     * @param claims Additional claims to include in the token
     * @param userDetails User details to extract username from
     * @return The generated JWT token string
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10)) // 10h
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Validates a JWT token by checking if the username matches and the token is not expired.
     * 
     * @param token The JWT token to validate
     * @param userDetails User details to compare against
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}