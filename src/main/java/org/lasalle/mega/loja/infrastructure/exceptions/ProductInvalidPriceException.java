package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductInvalidPriceException extends RuntimeException {

    public ProductInvalidPriceException(String message) {
        super(message);
    }

}
