package org.leafall.authservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.leafall.authservice.utils.ExceptionUtils.toError;

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

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handler(ExpiredJwtException ex, WebRequest request){
        return new ResponseEntity<>("Token is expired", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenIsUsedException.class)
    public ResponseEntity<Object> handler(TokenIsUsedException ex, WebRequest request){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
