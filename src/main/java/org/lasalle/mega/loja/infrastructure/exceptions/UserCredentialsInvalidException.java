package org.lasalle.mega.loja.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserCredentialsInvalidException extends RuntimeException {

    public UserCredentialsInvalidException(String email) {
        super("A senha salva no sistema não bate com a recebida para o email " + email);
    }

}
