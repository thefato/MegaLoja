package org.lasalle.mega.loja.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserCredentialsNotFoundException extends RuntimeException {

    public UserCredentialsNotFoundException(String message) {
        super("O usuario com email %s não foi encontrado".formatted(message));
    }

}
