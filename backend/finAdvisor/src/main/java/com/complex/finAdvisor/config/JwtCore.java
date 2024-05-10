package com.complex.finAdvisor.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtCore {
    @Value("${jwt-token.secret}")
    private String secret;
    @Value("${jwt-token.lifetime}")
    private int lifetime;
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //String secret = Encoders.BASE64.encode(key.getEncoded());

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public String getLoginFromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
