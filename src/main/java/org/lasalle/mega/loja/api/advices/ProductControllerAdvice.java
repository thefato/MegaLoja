package org.lasalle.mega.loja.api.advices;

import org.lasalle.mega.loja.domain.vo.ErrorInfo;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductCategoryNotExistsException;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductInvalidAmountException;
import org.lasalle.mega.loja.infrastructure.exceptions.ProductInvalidPriceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice extends BaseControllerAdvice {

    @ExceptionHandler({
            ProductInvalidPriceException.class,
            ProductInvalidAmountException.class,
            ProductCategoryNotExistsException.class
    })
    public ResponseEntity<ErrorInfo> handleInvalidPrice(Exception ex) {
        return getResponse(ex, HttpStatus.BAD_REQUEST);
    }

}
