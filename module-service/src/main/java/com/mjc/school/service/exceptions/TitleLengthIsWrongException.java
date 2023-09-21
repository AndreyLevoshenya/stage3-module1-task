package com.mjc.school.service.exceptions;

public class TitleLengthIsWrongException extends Exception {
    private static final String errorCode = "000003";

    public TitleLengthIsWrongException() {
    }

    public TitleLengthIsWrongException(String message) {
        super(message);
    }

    public TitleLengthIsWrongException(Throwable throwable) {
        super(throwable);
    }

    public TitleLengthIsWrongException(String message, Throwable throwable) {
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
