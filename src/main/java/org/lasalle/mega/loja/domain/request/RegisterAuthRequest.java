package org.lasalle.mega.loja.domain.request;

public record RegisterAuthRequest(String name, String document, String email, String password) {

    public LoginAuthRequest toLoginRequest() {
        return new LoginAuthRequest(email, password);
    }

}
