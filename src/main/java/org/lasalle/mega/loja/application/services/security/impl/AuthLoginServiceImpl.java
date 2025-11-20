package org.lasalle.mega.loja.application.services.security.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lasalle.mega.loja.application.repository.UserCredentialsRepository;
import org.lasalle.mega.loja.application.services.security.AuthLoginService;
import org.lasalle.mega.loja.application.services.security.JwtService;
import org.lasalle.mega.loja.domain.entity.UserCredentialsEntity;
import org.lasalle.mega.loja.domain.request.LoginAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;
import org.lasalle.mega.loja.infrastructure.exceptions.UserCredentialsInvalidException;
import org.lasalle.mega.loja.infrastructure.exceptions.UserCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLoginServiceImpl implements AuthLoginService {

    private final UserCredentialsRepository credentialsRepository;

    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public UserAuthResponse executeUserLogin(LoginAuthRequest authRequest) {
        UserCredentialsEntity credentials = credentialsRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new UserCredentialsNotFoundException(authRequest.email()));

        if (!passwordEncoder.matches(authRequest.password(), credentials.getPasswordHash())) {
            log.warn("m=executeUserLogin, a senha do usuario não bate com a senha salva no banco {}", authRequest.email());
            throw new UserCredentialsInvalidException(authRequest.email());
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password());
        Authentication auth = authManager.authenticate(authentication);

        List<String> scopes = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.contains("SCOPE_") ?
                        a.substring("SCOPE_".length()) :
                        a)
                .sorted()
                .toList();

        return UserAuthResponse.builder()
                .token(jwtService.generateAccessToken(auth))
                .scopes(scopes)
                .build();
    }
}
