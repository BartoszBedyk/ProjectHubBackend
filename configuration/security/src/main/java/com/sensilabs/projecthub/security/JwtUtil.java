package com.sensilabs.projecthub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecurityProps securityProps;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer";

    private final JwtParser jwtParser;

    public JwtUtil(SecurityProps securityProps) {
        this.securityProps = securityProps;
        this.jwtParser = Jwts.parser().setSigningKey(securityProps.getSecretKey());
    }

    public String generateToken(String userId) {

        Claims claims = Jwts.claims().setSubject(userId);
        Instant tokenCreateTime = Instant.now();
        Instant tokenValidity = tokenCreateTime.plus(securityProps.getTokenExpiration(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(tokenValidity))
                .signWith(SignatureAlgorithm.HS256, securityProps.getSecretKey())
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(String token) {
        return parseJwtClaims(token);
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        return claims.getExpiration().after(new Date());
    }
}
