package org.lasalle.mega.loja.infrastructure.exceptions;

public class ProductCategoryNotFoundException extends RuntimeException {

    public ProductCategoryNotFoundException() {
        super("A categoria informada não existe");
    }

}
