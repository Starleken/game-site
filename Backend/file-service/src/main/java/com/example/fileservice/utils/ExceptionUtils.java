package com.example.fileservice.utils;

import com.example.fileservice.exception.Error;
import com.example.fileservice.exception.NotFoundException;

public abstract class ExceptionUtils {

    public static NotFoundException getNotFoundException(Class clazz) {
        return new NotFoundException(clazz.getSimpleName() + " not found");
    }

    public static Error toError(Exception ex) {
        Error error = new Error();
        error.getErrors().add(ex.getMessage());

        return error;
    }
}
