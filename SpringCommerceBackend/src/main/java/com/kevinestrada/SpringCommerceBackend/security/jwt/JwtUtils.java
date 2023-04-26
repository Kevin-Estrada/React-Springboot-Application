package com.kevinestrada.SpringCommerceBackend.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kevinestrada.SpringCommerceBackend.security.services.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecommerce.app.jwtExpirationMs}")
    private int jwtExpirationsMs;

    // public String extractUsername(String token) {
    // return extractClaim(token, Claims::getSubject);
    // }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    // final Claims claims = extractAllClaims(token);
    // return claimsResolver.apply(claims);
    // }

    // public String generateToken(UserDetails userDetails) {
    // return generateToken(new HashMap<>(), userDetails);
    // }

    // public String generateToken(Map<String, Object> extraClaims, UserDetails
    // userDetails) {
    // return
    // Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
    // .setIssuedAt(new Date(System.currentTimeMillis()))
    // .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
    // .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    // }

    // public boolean isTokenValid(String token, UserDetails userDetails) {
    // final String username = extractUsername(token);
    // return (username.equals(userDetails.getUsername())) &&
    // !isTokenExpired(token);
    // }

    // private boolean isTokenExpired(String token) {
    // return extractExpiration(token).before(new Date());
    // }

    // private Date extractExpiration(String token) {
    // return extractClaim(token, Claims::getExpiration);
    // }

    // private Claims extractAllClaims(String token) {
    // return
    // Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    // }

    // private Key getSignInKey() {
    // byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    // return Keys.hmacShaKeyFor(keyBytes);
    // }

    public String generateJwtToken(Authentication authentication) {
        System.out.println("I am in the generateJwtToken method and the secret is: " + jwtSecret);
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String testJwt = Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        System.out.println("The testJwt is the following" + testJwt);
        return testJwt;
        // return
        // Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new
        // Date())
        // .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
        // .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
        System.out.println("The username from token is: "
                + Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("I am in the validate Jwt Token method with authToken: " + authToken);
            System.out.println("The jwt secret is: " + jwtSecret);
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            System.out.println("I got pass the jwts.parser");
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("This is a general exception caught: {}", e.getMessage());
        }

        return false;
    }
}
