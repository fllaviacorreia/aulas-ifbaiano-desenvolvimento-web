package com.project.payment.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtEncoder encoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiresIn = 7L;

        String scopes = authentication
                            .getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet
                        .builder()
                        .issuer("payment-api")
                        .issuedAt(now)
                        .expiresAt(now.plus(Duration.ofDays(expiresIn)))
                        .subject(authentication.getName())
                        .claim("scope",scopes)
                        .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}