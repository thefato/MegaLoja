package org.lasalle.mega.loja.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.security.services.AuthLoginService;
import org.lasalle.mega.loja.application.security.services.AuthRegisterService;
import org.lasalle.mega.loja.domain.request.AuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;
import org.lasalle.mega.loja.domain.response.UserScopesResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthLoginService authLoginService;

    private final AuthRegisterService authRegisterService;

    @Operation(summary = "Efetua o login, gerando o token JWT")
    @PostMapping("/login")
    public ResponseEntity<UserScopesResponse> login(@RequestBody AuthRequest authRequest) {
        UserAuthResponse userAuthResponse = authLoginService.executeUserLogin(authRequest);
        UserScopesResponse response = new UserScopesResponse(userAuthResponse.scopes());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + userAuthResponse.token());

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

    @Operation(summary = "Registra um novo usuario no sistema")
    @PostMapping("/register")
    public ResponseEntity<UserScopesResponse> register(@RequestBody AuthRequest authRequest) {
        UserAuthResponse userAuthResponse = authRegisterService.executeUserRegister(authRequest);
        UserScopesResponse response = new UserScopesResponse(userAuthResponse.scopes());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + userAuthResponse.token());

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

}
