package com.bondarenko.universityAssigment.library;

public class UnknownPatronException extends RuntimeException {
    public UnknownPatronException(String errorMessage) {
        super(errorMessage);
    }

    public UnknownPatronException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
