package org.lasalle.mega.loja.api.advices;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseControllerAdvice {

    protected ResponseEntity<ErrorInfo> getResponse(Exception ex, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorInfo(httpStatus.value(), ex.getMessage()));
    }

}
