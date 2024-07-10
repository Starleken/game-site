package com.example.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.example.fileservice.utils.ExceptionUtils.toError;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handler(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.NOT_FOUND);
    }
}
