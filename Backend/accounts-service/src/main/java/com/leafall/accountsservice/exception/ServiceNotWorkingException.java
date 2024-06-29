package com.leafall.accountsservice.exception;

public class ServiceNotWorkingException extends RuntimeException {

    public ServiceNotWorkingException(String message) {
        super(message);
    }
}
