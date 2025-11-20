package org.lasalle.mega.loja.infrastructure.exceptions;

public class StoreInvalidNameException extends RuntimeException {

    public StoreInvalidNameException() {
        super("O nome do produto não pode ser vazio");
    }

}
