package org.leafall.authservice.utils;

import org.leafall.authservice.exception.EntityNotFoundException;
import org.leafall.authservice.exception.Error;
import org.leafall.authservice.exception.IllegalActionException;
import org.leafall.authservice.exception.TokenIsUsedException;

public abstract class ExceptionUtils {

    public static void throwIllegalActionException(String message) {
        throw new IllegalActionException(message);
    }

    public static EntityNotFoundException getEntityNotFoundException(Class clazz) {
        return new EntityNotFoundException(clazz.getSimpleName() + " not found");
    }

    public static EntityNotFoundException getEntityNotFoundException(Class clazz, long id) {
        return new EntityNotFoundException(clazz.getSimpleName() + " not found by id: " + id);
    }

    public static void throwTokenIsUsedException(String errorMessage) {
        throw new TokenIsUsedException(errorMessage);
    }

    public static Error toError(Exception ex) {
        Error error = new Error();
        error.getErrors().add(ex.getMessage());

        return error;
    }
}
