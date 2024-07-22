package org.leafall.authservice.exception;

public class TokenIsUsedException extends RuntimeException {

    public TokenIsUsedException(String message) {
        super(message);
    }
}
