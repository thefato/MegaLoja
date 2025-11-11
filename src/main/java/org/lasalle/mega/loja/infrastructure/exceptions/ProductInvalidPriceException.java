package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductInvalidPriceException extends RuntimeException {

    public ProductInvalidPriceException() {
        super("O valor não pode ser menor que zero");
    }

}
