package org.lasalle.mega.loja.infrastructure.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String cartToken) {
        super("Carrinho não encontrado para o token: " + cartToken);
    }

}
