package org.lasalle.mega.loja.application.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    Jwt parseToken(String token);

}
