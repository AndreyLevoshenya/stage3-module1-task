package com.mjc.school.service.exceptions;

public class AuthorNotFoundException extends Exception {
    private final String errorCode = "000002";

    public AuthorNotFoundException() {

    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public AuthorNotFoundException(String message, Throwable throwable) {
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
