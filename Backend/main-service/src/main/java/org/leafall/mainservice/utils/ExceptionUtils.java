package org.leafall.mainservice.utils;

import org.leafall.mainservice.exception.EntityNotFoundException;
import org.leafall.mainservice.exception.Error;
import org.leafall.mainservice.exception.IllegalActionException;

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
