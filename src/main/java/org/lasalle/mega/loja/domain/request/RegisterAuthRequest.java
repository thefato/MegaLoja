package org.lasalle.mega.loja.domain.request;

import jakarta.validation.constraints.NotNull;

public record RegisterAuthRequest(@NotNull String name, @NotNull String document, @NotNull String email, @NotNull String password) {

    public LoginAuthRequest toLoginRequest() {
        return new LoginAuthRequest(email, password);
    }

}
