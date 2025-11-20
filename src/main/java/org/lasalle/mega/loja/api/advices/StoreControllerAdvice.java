package org.lasalle.mega.loja.api.advices;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreInvalidNameException;
import org.lasalle.mega.loja.infrastructure.exceptions.StoreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StoreControllerAdvice extends BaseControllerAdvice {

    @ExceptionHandler({
            StoreInvalidNameException.class
    })
    public ResponseEntity<ErrorInfo> handleInvalidPrice(Exception ex) {
        return getResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            StoreNotFoundException.class
    })
    public ResponseEntity<ErrorInfo> handleNotFound(Exception ex) {
        return getResponse(ex, HttpStatus.NOT_FOUND);
    }

}
