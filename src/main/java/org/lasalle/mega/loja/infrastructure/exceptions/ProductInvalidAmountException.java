package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductInvalidAmountException extends RuntimeException {

    public ProductInvalidAmountException(String message) {
        super(message);
    }

}
