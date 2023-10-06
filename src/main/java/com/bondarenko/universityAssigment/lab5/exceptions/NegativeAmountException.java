package com.bondarenko.universityAssigment.lab5.exceptions;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(String errorMessage) {
        super(errorMessage);
    }

    public NegativeAmountException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
