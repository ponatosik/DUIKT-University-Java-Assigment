package com.bondarenko.universityAssigment.lab6.Theater;

import java.util.Optional;

public interface Theater {
    int getHalls();
    int getRows();
    int getSeats();

    void bookSeats(int hallNumber, int row, int... seats);
    void cancelBooking(int hallNumber, int row, int... seats);
    boolean isSeatBooked(int hallNumber, int row, int seat);
    boolean checkAvailability(int hallNumber, int row, int numSeats);
    void printSeatingArrangement(int hallNumber);
    Optional<SeatIndex> findBestAvailable(int hallNumber, int numSeats);
    Optional<SeatIndex> autoBook(int hallNumber, int numSeats);
}
