package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductCategoryNotExistsException extends RuntimeException {

    public ProductCategoryNotExistsException(String message) {
        super(message);
    }

}
