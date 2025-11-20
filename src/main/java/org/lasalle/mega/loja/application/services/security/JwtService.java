package org.lasalle.mega.loja.application.services.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    Jwt parseToken(String token);

}
