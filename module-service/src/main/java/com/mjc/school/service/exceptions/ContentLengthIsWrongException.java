package com.mjc.school.service.exceptions;

public class ContentLengthIsWrongException extends Exception {
    private static final String errorCode = "000004";

    public ContentLengthIsWrongException() {
    }

    public ContentLengthIsWrongException(String message) {
        super(message);
    }

    public ContentLengthIsWrongException(Throwable throwable) {
        super(throwable);
    }

    public ContentLengthIsWrongException(String message, Throwable throwable) {
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
