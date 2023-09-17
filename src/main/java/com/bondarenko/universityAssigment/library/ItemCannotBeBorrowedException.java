package com.bondarenko.universityAssigment.library;

public class ItemCannotBeBorrowedException extends Exception {
    public ItemCannotBeBorrowedException(String errorMessage) {
        super(errorMessage);
    }

    public ItemCannotBeBorrowedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
