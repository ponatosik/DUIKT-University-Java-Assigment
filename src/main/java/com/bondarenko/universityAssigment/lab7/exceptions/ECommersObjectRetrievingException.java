package com.bondarenko.universityAssigment.lab7.exceptions;

public class ECommersObjectRetrievingException  extends  ECommersPlatformException{
    public ECommersObjectRetrievingException(String errorMessage) {
        super(errorMessage);
    }

    public ECommersObjectRetrievingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
