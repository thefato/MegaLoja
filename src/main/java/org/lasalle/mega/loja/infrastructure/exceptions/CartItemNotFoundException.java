package org.lasalle.mega.loja.infrastructure.exceptions;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(Long productId) {
        super("Item do carrinho não encontrado para o produto: " + productId);
    }

}
