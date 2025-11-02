package org.lasalle.mega.loja.api.advice;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.lasalle.mega.loja.infrastructure.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> handle(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorInfo(HttpStatus.CONFLICT.value(), ex.getMessage()));
    }

}
