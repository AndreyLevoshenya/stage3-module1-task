package com.mjc.school.controller.exceptions;

public class IdIsWrongException extends Exception {
    private final String errorCode = "000005";

    public IdIsWrongException() {
    }

    public IdIsWrongException(String message) {
        super(message);
    }

    public IdIsWrongException(Throwable throwable) {
        super(throwable);
    }

    public IdIsWrongException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + super.getMessage();
    }
}
