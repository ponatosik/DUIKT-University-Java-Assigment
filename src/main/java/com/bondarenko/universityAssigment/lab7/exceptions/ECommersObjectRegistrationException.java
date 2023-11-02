package com.bondarenko.universityAssigment.lab7.exceptions;

public class ECommersObjectRegistrationException extends  ECommersPlatformException{
    public ECommersObjectRegistrationException(String errorMessage) {
        super(errorMessage);
    }

    public ECommersObjectRegistrationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
