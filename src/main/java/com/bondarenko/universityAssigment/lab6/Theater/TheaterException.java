package com.bondarenko.universityAssigment.lab6.Theater;

public class TheaterException extends RuntimeException {
    public TheaterException(String errorMessage) {
        super(errorMessage);
    }

    public TheaterException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
