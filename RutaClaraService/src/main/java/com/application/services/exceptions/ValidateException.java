package com.application.services.exceptions;

public class ValidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidateException(String msg) {
        super(msg);
    }
}
