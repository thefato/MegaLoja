package org.lasalle.mega.loja.infrastructure.exceptions;

public class UserCredentialsNotFoundException extends RuntimeException {

    public UserCredentialsNotFoundException(String message) {
        super("O usuario com email %s não foi encontrado".formatted(message));
    }

}
