package org.lasalle.mega.loja.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.lasalle.mega.loja.application.security.services.AuthLoginService;
import org.lasalle.mega.loja.application.security.services.AuthRegisterService;
import org.lasalle.mega.loja.domain.request.LoginAuthRequest;
import org.lasalle.mega.loja.domain.request.RegisterAuthRequest;
import org.lasalle.mega.loja.domain.response.UserAuthResponse;
import org.lasalle.mega.loja.domain.response.UserScopesResponse;
import org.lasalle.mega.loja.domain.vo.ErrorInfo;
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

    @PermitAll
    @Operation(summary = "Efetua o login, gerando o token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "A senha está incorreta",
                    content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
            @ApiResponse(responseCode = "404", description = "O usuario não existe",
                    content = @Content(schema = @Schema(implementation = ErrorInfo.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<UserScopesResponse> login(@RequestBody LoginAuthRequest authRequest) {
        UserAuthResponse userAuthResponse = authLoginService.executeUserLogin(authRequest);
        UserScopesResponse response = new UserScopesResponse(userAuthResponse.scopes());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + userAuthResponse.token());

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

    @PermitAll
    @Operation(summary = "Registra um novo usuario no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "O email já está cadastrado",
                    content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
    })
    @PostMapping("/register")
    public ResponseEntity<UserScopesResponse> register(@RequestBody RegisterAuthRequest authRequest) {
        UserAuthResponse userAuthResponse = authRegisterService.executeUserRegister(authRequest);
        UserScopesResponse response = new UserScopesResponse(userAuthResponse.scopes());

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + userAuthResponse.token());

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    }

}
