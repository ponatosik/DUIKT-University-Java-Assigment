package com.bondarenko.universityAssigment.lab2.exceptions;

public class ItemCannotBeBorrowedException extends RuntimeException {
    public ItemCannotBeBorrowedException(String errorMessage) {
        super(errorMessage);
    }

    public ItemCannotBeBorrowedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
