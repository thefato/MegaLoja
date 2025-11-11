package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductInvalidNameException extends RuntimeException {

    public ProductInvalidNameException() {
        super("O nome do produto não pode ser vazio");
    }

}
