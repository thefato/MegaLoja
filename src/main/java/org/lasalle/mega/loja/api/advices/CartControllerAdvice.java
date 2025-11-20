package org.lasalle.mega.loja.api.advices;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.lasalle.mega.loja.infrastructure.exceptions.CartItemNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.CartNotFoundException;
import org.lasalle.mega.loja.infrastructure.exceptions.CartUserMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CartControllerAdvice extends BaseControllerAdvice {

    @ExceptionHandler({
            CartUserMismatchException.class
    })
    public ResponseEntity<ErrorInfo> handleMismatchCart(Exception ex) {
        return getResponse(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            CartItemNotFoundException.class,
            CartNotFoundException.class
    })
    public ResponseEntity<ErrorInfo> handleNotFound(Exception ex) {
        return getResponse(ex, HttpStatus.NOT_FOUND);
    }

}
