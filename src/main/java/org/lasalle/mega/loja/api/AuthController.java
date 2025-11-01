package org.lasalle.mega.loja.api;

import io.swagger.v3.oas.annotations.Operation;
import org.lasalle.mega.loja.domain.dto.TokenDTO;
import org.lasalle.mega.loja.domain.request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Operation(summary = "Efetua o login, gerando o token JWT")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ofNullable(null);
    }

    @Operation(summary = "Registra um novo usuario no sistema")
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ofNullable(null);
    }

}
