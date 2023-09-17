package com.bondarenko.universityAssigment.library;

public class UnknownPatronException extends Exception {
    public UnknownPatronException(String errorMessage) {
        super(errorMessage);
    }

    public UnknownPatronException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
