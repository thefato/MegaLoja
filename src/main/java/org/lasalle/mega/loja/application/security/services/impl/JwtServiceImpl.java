package org.lasalle.mega.loja.application.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.security.services.JwtService;
import org.lasalle.mega.loja.domain.vo.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant exp = now.plus(jwtProperties.accessTokenMinutes(), ChronoUnit.MINUTES);

        List<String> scopes = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.contains("SCOPE_") ?
                        a.substring("SCOPE_".length()) :
                        a)
                .sorted()
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtProperties.issuer())
                .audience(List.of(jwtProperties.audience()))
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(exp)
                .id(UUID.randomUUID().toString())
                .claim("scope", String.join(" ", scopes))
                .build();

        JwsHeader jws = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jws, claims)).getTokenValue();
    }

    @Override
    public Jwt parseToken(String token) {
        return jwtDecoder.decode(token);
    }
}
