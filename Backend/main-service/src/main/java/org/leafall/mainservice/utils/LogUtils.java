package org.leafall.mainservice.utils;

public abstract class LogUtils {

    public static String getRequest(Class entity, String methodName, Object dto) {
        return entity.getSimpleName() + " " + methodName + " request: " + dto.toString();
    }

    public static String getRequest(Class entity, String methodName) {
        return entity.getSimpleName() + " " + methodName + " requested";
    }

    public static String getRequest(Class entity, String methodName, String fieldBy, Object value) {
        return entity.getSimpleName() + " " + methodName + " request. " + fieldBy + ": " + value.toString();
    }

    public static String getRequest(Class entity, int count) {
        return "The number of " + entity.getSimpleName() + " found: " + count;
    }

    public static String getRequest(String object, String methodName, String field, Object value) {
        return object + " " + methodName + ". " + field + ": " + value.toString();
    }

    public static String getResultRequest(Class entity, String operation, Object dto) {
        return entity.getSimpleName() + " " + operation + ". " + dto.toString();
    }

    public static String getResultRequest(Class entity, String operation, String fieldBy, Object value) {
        return entity.getSimpleName() + " " + operation + ". " + fieldBy + ":" + value;
    }
}
