package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductInvalidAmountException extends RuntimeException {

    public ProductInvalidAmountException() {
        super("A quantidade do produto não pode ser menor que zero");
    }

}
