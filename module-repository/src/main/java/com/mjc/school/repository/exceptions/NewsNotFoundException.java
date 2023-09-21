package com.mjc.school.repository.exceptions;

public class NewsNotFoundException extends Exception {
    private static final String errorCode = "000001";

    public NewsNotFoundException() {

    }

    public NewsNotFoundException(String message) {
        super(message);
    }

    public NewsNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public NewsNotFoundException(String message, Throwable throwable) {
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
