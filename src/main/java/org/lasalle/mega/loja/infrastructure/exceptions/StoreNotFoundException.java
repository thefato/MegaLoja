package org.lasalle.mega.loja.infrastructure.exceptions;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(Integer id) {
        super("Loja não encontrada para o id: " + id);
    }

}
