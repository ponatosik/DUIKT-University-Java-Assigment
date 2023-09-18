package com.bondarenko.universityAssigment.lab2.exceptions;

public class UnknownPatronException extends RuntimeException {
    public UnknownPatronException(String errorMessage) {
        super(errorMessage);
    }

    public UnknownPatronException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
