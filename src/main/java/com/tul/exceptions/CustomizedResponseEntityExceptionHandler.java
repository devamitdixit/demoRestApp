package com.tul.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Errors> handleNotFoundException(ProductNotFoundException ex) {
        Errors error = new Errors(HttpStatus.NO_CONTENT.toString(), "Product Not found", HttpStatus.NO_CONTENT.toString() );
        return new ResponseEntity<Errors>(error, HttpStatus.NO_CONTENT);
    }

}
