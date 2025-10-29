package org.lasalle.mega.loja.application.security;

import org.lasalle.mega.loja.domain.request.AuthRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    String generateAccessToken(Authentication authentication);

    Jwt parseToken(String token);

}
