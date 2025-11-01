package org.lasalle.mega.loja.infrastructure.exceptions;

public class UserGroupNotFoundException extends RuntimeException {

    public UserGroupNotFoundException(String message) {
        super("O grupo de permissão do usuario %s não foi encontrado".formatted(message));
    }

}
