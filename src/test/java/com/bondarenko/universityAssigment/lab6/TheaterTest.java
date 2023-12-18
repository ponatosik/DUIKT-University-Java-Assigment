package com.bondarenko.universityAssigment.lab6;

import com.bondarenko.universityAssigment.lab6.Theater.SeatIndex;
import com.bondarenko.universityAssigment.lab6.Theater.Implementation.UnsafeTheater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TheaterTest {
    UnsafeTheater theater;

    @BeforeEach
    void setUp() {
        theater = new UnsafeTheater(5, 10, 20);
    }

    @Test
    void BookSeats_ValidSeats_ShouldSetThemBooked() {
        int hallNumber = 0;
        int row = 1;

        theater.bookSeats(hallNumber, row, 1, 2, 3, 4, 10);

        assertTrue(theater.isSeatBooked(hallNumber, row, 1));
        assertTrue(theater.isSeatBooked(hallNumber, row, 2));
        assertTrue(theater.isSeatBooked(hallNumber, row, 3));
        assertTrue(theater.isSeatBooked(hallNumber, row, 4));
        assertTrue(theater.isSeatBooked(hallNumber, row, 10));
    }

    @Test
    void BookSeats_InvalidSeats_ShouldThrow() {
        int hallNumber = 9999;
        int row = 9999;
        int seat = 9999;

        assertThrows(Exception.class, () -> theater.bookSeats(hallNumber, row, seat));
    }

    @Test
    void CancelBooking_BookedSeats_ShouldSetThemUnbooked() {
        int hallNumber = 0;
        int row = 1;
        theater.bookSeats(hallNumber, row, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        theater.cancelBooking(hallNumber, row, 3, 5, 7);

        assertFalse(theater.isSeatBooked(hallNumber, row, 3));
        assertFalse(theater.isSeatBooked(hallNumber, row, 5));
        assertFalse(theater.isSeatBooked(hallNumber, row, 7));
    }

    @Test
    void CheckAvailability_AvailableSeats_ShouldReturnTrue() {
        int hallNumber = 0;
        int row = 1;
        int numSeats = 5;
        theater.bookSeats(hallNumber, row, 0, 10);

        var actual = theater.checkAvailability(hallNumber,row, numSeats);

        assertTrue(actual);
    }

    @Test
    void CheckAvailability_UnavailableSeats_ShouldReturnFalse() {
        int hallNumber = 0;
        int row = 0;
        theater.bookSeats(hallNumber, row, 0, 5, 10, 15);

        var actual = theater.checkAvailability(hallNumber, row, 10);

        assertFalse(actual);
    }

    @Test
    void PrintSeatingArrangement_ShouldNotThrow() {
        int hallNumber = 0;
        int row = 0;
        theater.bookSeats(hallNumber, row, 0, 5, 10, 15);

        assertDoesNotThrow(() -> theater.printSeatingArrangement(0));
    }

    @Test
    void FindBestAvailable_HallWithBookedSeats_ShouldReturnBest(){
        int hall = 0;
        int numSeats = 3;
        var expected = new SeatIndex();
        expected.row = 0;
        expected.seat = 7;
        theater.bookSeats(hall, 0, 1, 6, 10, 15);

        var actual = theater.findBestAvailable(hall, numSeats);

        assertTrue(actual.isPresent());
        assertEquals(expected.row, actual.get().row);
        assertEquals(expected.seat, actual.get().seat);
    }

    @Test
    void FindBestAvailable_HallWithoutRequiredNumberOfSeats_ShouldReturnEmptyOptional(){
        int hall = 0;
        int numSeats = 999;

        var actual = theater.findBestAvailable(hall, numSeats);

        assertFalse(actual.isPresent());
    }

    @Test
    void AutoBook_HallWithBookedSeats_ShouldReturnAndBookBest(){
        theater.bookSeats(0, 0, 1, 6, 10, 15);
        var expected = new SeatIndex();
        expected.row = 0;
        expected.seat = 7;

        var actual = theater.autoBook(0, 3);

        assertTrue(actual.isPresent());
        assertEquals(expected.row, actual.get().row);
        assertEquals(expected.seat, actual.get().seat);
        assertTrue(theater.isSeatBooked(0, 0, 7));
        assertTrue(theater.isSeatBooked(0, 0, 8));
        assertTrue(theater.isSeatBooked(0, 0, 9));
    }

    @Test
    void AutoBook_HallWithoutRequiredNumberOfSeats_ShouldReturnEmptyOptional(){
        int hall = 0;
        int numSeats = 999;

        var actual = theater.autoBook(hall, numSeats);

        assertFalse(actual.isPresent());
    }
}