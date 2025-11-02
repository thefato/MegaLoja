package org.lasalle.mega.loja.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserGroupNotFoundException extends RuntimeException {

    public UserGroupNotFoundException(String message) {
        super("O grupo de permissão do usuario %s não foi encontrado".formatted(message));
    }

}
