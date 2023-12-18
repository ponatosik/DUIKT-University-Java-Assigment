package com.bondarenko.universityAssigment.lab6.Theater.Implementation;

import com.bondarenko.universityAssigment.lab6.Theater.Exceptions.InvalidBookingException;
import com.bondarenko.universityAssigment.lab6.Theater.Exceptions.TheaterException;
import com.bondarenko.universityAssigment.lab6.Theater.SeatIndex;
import com.bondarenko.universityAssigment.lab6.Theater.Theater;

import java.util.Optional;

// Wrapper around UnsafeTheater that provides better exception and user-friendly indexing (starts from 1 instead of 0)
public class SafeTheater implements Theater {
    private final Theater theater;

    public SafeTheater(Theater baseTheater) {
        theater = baseTheater;
    }
    public SafeTheater(int halls, int rows, int seats) {
        this(new UnsafeTheater(halls, rows, seats));
    }

    public int getHalls() {
        return theater.getHalls();
    }
    public int getRows() {
        return theater.getRows();
    }
    public int getSeats() {
        return theater.getSeats();
    }

    public void bookSeats(int safeHallIndex, int safeRowIndex, int... seats) {
        int hallNumber = shiftIncomingIndex(safeHallIndex);
        int  row = shiftIncomingIndex(safeRowIndex);
        shiftIncomingIndexes(seats);

        assertValidHallNumber(hallNumber);
        assertValidRowNumber(row);

        for (int unsafeSeatIndex : seats) {
            if(unsafeSeatIndex >= getSeats() || unsafeSeatIndex < 0) {
                throw new InvalidBookingException(safeHallIndex, safeRowIndex, shiftOutgoingIndex(unsafeSeatIndex), "Invalid seat");
            }
            if(theater.isSeatBooked(hallNumber, row, unsafeSeatIndex)) {
                throw new InvalidBookingException(safeHallIndex, safeRowIndex, shiftOutgoingIndex(unsafeSeatIndex), "Seat is already booked");
            }
        }

        theater.bookSeats(hallNumber, row, seats);
    }
    public void cancelBooking(int safeHallIndex, int safeRowIndex, int... seats) {
        int hallNumber = shiftIncomingIndex(safeHallIndex);
        int row = shiftIncomingIndex(safeRowIndex);
        shiftIncomingIndexes(seats);

        assertValidHallNumber(hallNumber);
        assertValidRowNumber(row);

        for (int unsafeSeatIndex : seats) {
            if (unsafeSeatIndex >= getSeats() || unsafeSeatIndex < 0) {
                throw new InvalidBookingException(safeHallIndex, safeRowIndex, shiftOutgoingIndex(unsafeSeatIndex), "Invalid seat");
            }
            if(!theater.isSeatBooked(hallNumber, row, unsafeSeatIndex)) {
                throw new InvalidBookingException(safeHallIndex, safeRowIndex, shiftOutgoingIndex(unsafeSeatIndex), "Seat was not booked");
            }
        }

        theater.cancelBooking(hallNumber, row, seats);
    }
    public boolean isSeatBooked(int safeHallNumber, int safeRow, int safeSeat) {
        int hallNumber = shiftIncomingIndex(safeHallNumber);
        int row = shiftIncomingIndex(safeRow);
        int seat = shiftIncomingIndex(safeSeat);

        assertValidHallNumber(hallNumber);
        assertValidRowNumber(row);
        assertValidSeatNumber(seat);

        return theater.isSeatBooked(hallNumber, row, seat);
    }
    public boolean checkAvailability(int safeHallNumber, int safeRow, int numSeats) {
        int hallNumber = shiftIncomingIndex(safeHallNumber);
        int row = shiftIncomingIndex(safeRow);

        assertValidHallNumber(hallNumber);
        assertValidRowNumber(row);

        return theater.checkAvailability(hallNumber, row, numSeats);
    }
    public void printSeatingArrangement(int safeHallNumber) {
        int hallNumber = shiftIncomingIndex(safeHallNumber);
        assertValidHallNumber(hallNumber);

        theater.printSeatingArrangement(hallNumber);
    }
    public Optional<SeatIndex> findBestAvailable(int safeHallNumber, int numSeats) {
        int hallNumber = shiftIncomingIndex(safeHallNumber);
        assertValidHallNumber(hallNumber);

        return theater.findBestAvailable(hallNumber, numSeats).map(this::shiftOutgoingIndex);
    }
    public Optional<SeatIndex> autoBook(int safeHallNumber, int numSeats) {
        int hallNumber = shiftIncomingIndex(safeHallNumber);
        assertValidHallNumber(hallNumber);

        return theater.autoBook(hallNumber, numSeats).map(this::shiftOutgoingIndex);
    }

    private int shiftIncomingIndex(int safeIndex) {
        return safeIndex - 1;
    }
    private void shiftIncomingIndexes(int[] safeIndexes) {
        for (int i = 0; i < safeIndexes.length; i++) {
            safeIndexes[i]--;
        }
    }
    private int shiftOutgoingIndex(int unsafeIndex) {
        return unsafeIndex + 1;
    }
    private SeatIndex shiftOutgoingIndex(SeatIndex unsafeIndex) {
        SeatIndex safeIndex = new SeatIndex();
        safeIndex.setSeat(shiftOutgoingIndex(unsafeIndex.getSeat()));
        safeIndex.setRow(shiftOutgoingIndex(unsafeIndex.getRow()));
        return safeIndex;
    }

    private void assertValidHallNumber(int unsafeIndex) throws InvalidBookingException{
        if(unsafeIndex < 0 || unsafeIndex >= getHalls()) {
            throw new TheaterException("Hall " + shiftOutgoingIndex(unsafeIndex) + "doesn't exist");
        }
    }
    private void assertValidRowNumber(int unsafeIndex) throws InvalidBookingException{
        if(unsafeIndex < 0 || unsafeIndex >= getRows()) {
            throw new TheaterException("Row " + shiftOutgoingIndex(unsafeIndex) + "doesn't exist");
        }
    }
    private void assertValidSeatNumber(int unsafeIndex) throws InvalidBookingException{
        if(unsafeIndex < 0 || unsafeIndex >= getSeats()) {
            throw new TheaterException("Seat " + shiftOutgoingIndex(unsafeIndex) + "doesn't exist");
        }
    }
}
