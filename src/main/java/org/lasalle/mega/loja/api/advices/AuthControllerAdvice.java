package org.lasalle.mega.loja.api.advices;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.lasalle.mega.loja.infrastructure.exceptions.UserAlreadyExistsException;
import org.lasalle.mega.loja.infrastructure.exceptions.UserCredentialsInvalidException;
import org.lasalle.mega.loja.infrastructure.exceptions.UserCredentialsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorInfo> handleAlreadyExists(UserAlreadyExistsException ex) {
        return getResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserCredentialsNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundCredentials(UserCredentialsNotFoundException ex) {
        return getResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCredentialsInvalidException.class)
    public ResponseEntity<ErrorInfo> handleInvalidCredentials(UserCredentialsInvalidException ex) {
        return getResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    private static ResponseEntity<ErrorInfo> getResponse(Exception ex, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorInfo(httpStatus.value(), ex.getMessage()));
    }

}
