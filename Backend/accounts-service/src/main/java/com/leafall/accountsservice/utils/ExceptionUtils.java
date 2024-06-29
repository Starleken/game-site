package com.leafall.accountsservice.utils;

import com.leafall.accountsservice.exception.EntityNotFoundException;
import com.leafall.accountsservice.exception.Error;
import com.leafall.accountsservice.exception.IllegalActionException;
import com.leafall.accountsservice.exception.ServiceNotWorkingException;

public abstract class ExceptionUtils {

    public static void throwIllegalActionException(String message) {
        throw new IllegalActionException(message);
    }

    public static EntityNotFoundException getEntityNotFoundException(Class clazz) {
        return new EntityNotFoundException(clazz.getSimpleName() + " not found");
    }

    public static Error toError(Exception ex) {
        Error error = new Error();
        error.getErrors().add(ex.getMessage());

        return error;
    }
}
