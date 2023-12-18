package com.bondarenko.universityAssigment.lab6.Theater.Exceptions;

import lombok.Getter;

@Getter
public class InvalidBookingException extends TheaterException {
    private final int hallIndex;
    private final int rowIndex;
    private final int seatIndex;
    private final String reason;

    public InvalidBookingException(int hall, int row, int seat, String reason) {
        super(getErrorMessage(hall, row, seat, reason));
        hallIndex = hall;
        rowIndex = row;
        seatIndex = seat;
        this.reason = reason;
    }

    public InvalidBookingException(int hall, int row, int seat, String reason, Throwable err) {
        super(getErrorMessage(hall, row, seat, reason), err);
        hallIndex = hall;
        rowIndex = row;
        seatIndex = seat;
        this.reason = reason;
    }

    private static String getErrorMessage(int hall, int row, int seat, String reason) {
        return "Unable to book seat " + seat + " at row " + row + " at hall " + hall + " : " + reason;
    }
}
