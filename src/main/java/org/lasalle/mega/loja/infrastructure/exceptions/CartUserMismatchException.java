package org.lasalle.mega.loja.infrastructure.exceptions;

public class CartUserMismatchException extends RuntimeException {

    public CartUserMismatchException() {
        super("O carrinho já está vinculado a outro usuário.");
    }

}
