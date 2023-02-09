package com.rbc.digital.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StockIndexControllerAdvice  extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = NoStocksFoundException.class)
    protected ResponseEntity<String> handleEmptyData(RuntimeException ex) {
        String bodyOfResponse = String.format("{\"message\": \"Please upload data. Detail message is %s\"}",ex.getMessage());
        return new ResponseEntity<>( bodyOfResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = StockNotFoundException.class)
    protected ResponseEntity<Object> handleInvalidStock(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = String.format("{\"message\": \"Stock queried is not available in system. Detail message is %s\"}",ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
