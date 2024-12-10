package com.kuki.social_networking.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kuki.social_networking.service.implementation.CustomUserDetails;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

/**
 * Utility class for handling JWT tokens.
 */
@Component
public class JwtTokenUtils implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("10891732948712309471203497120471293847192834701932874709187240980918237409182374901827349081327409812740912847102983471029847102938740198347102894710932847109238701982374018923740918273409182734091827340981723409817234098172340981273409817234091872409182341")
    private String secret;

    /**
     * Extracts the JWT token from the HTTP request.
     *
     * @param request the HTTP request
     * @return the extracted JWT token, or null if not found
     */
    public static String getTokenFromRequest(HttpServletRequest request){
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return getTokenFromHeader(bearerToken);
    }

    /**
     * Extracts the token from the Authorization header.
     *
     * @param authorizationHeader the Authorization header
     * @return the extracted token, or null if not found
     */
    public static String getTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        return null;
    }

    /**
     * Extracts the user details from the JWT token.
     *
     * @param token The JWT token.
     * @return The user details.
     */
    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String username = claims.getSubject();
        List<Map<String, String>> rolesMap = claims.get("authorities", List.class);

        List<GrantedAuthority> authorities = rolesMap.stream()
            .map(roleMap -> new SimpleGrantedAuthority(roleMap.get("authority")))
            .collect(Collectors.toList());

        return new CustomUserDetails(username, "", authorities);
    }

    /**
     * Retrieves the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieves the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves a claim from the JWT token.
     *
     * @param token The JWT token.
     * @param claimsResolver The function to retrieve the claim.
     * @param <T> The type of the claim.
     * @return The claim.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the secret key used for signing the JWT token.
     *
     * @return the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails The user details.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails The user details.
     * @return The generated JWT token.
     */
    public String generateToken(UserDetails userDetails, Map<String, Object> additionalClaims) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        claims.putAll(additionalClaims);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Generates a JWT token with the given claims and subject.
     *
     * @param claims The claims to include in the token.
     * @param subject The subject of the token.
     * @return The generated JWT token.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject).issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(getKey(), SignatureAlgorithm.HS512).compact();
    }

    /**
     * Validates the JWT token.
     *
     * @param token The JWT token.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Retrieves the secret key used for signing the JWT token.
     *
     * @return the secret key
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

