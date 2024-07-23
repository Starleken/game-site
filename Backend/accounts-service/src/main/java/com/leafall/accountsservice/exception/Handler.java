package com.leafall.accountsservice.exception;

import com.leafall.accountsservice.utils.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.leafall.accountsservice.utils.ExceptionUtils.*;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handler(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<Error> handler(IllegalActionException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handler(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(toError(ex), HttpStatus.BAD_REQUEST);
    }
}
