package com.bondarenko.universityAssigment.lab6;

import com.bondarenko.universityAssigment.lab6.CommandLine.CommandLineCommand.ParameterType;
import com.bondarenko.universityAssigment.lab6.CommandLine.CommandLineInterfaceBuilder;
import com.bondarenko.universityAssigment.lab6.Theater.Implementation.SafeTheater;
import com.bondarenko.universityAssigment.lab6.Theater.Theater;

import java.io.PrintStream;
import java.util.List;

public class TheaterDemo {
    private static final Theater theater = new SafeTheater(5, 10, 20);

    public static void main(String[] args) {
        CommandLineInterfaceBuilder builder = new CommandLineInterfaceBuilder();
        PrintStream console = System.out;

        builder.addCommand("help", TheaterDemo::printHelp)
                .addCommand("info", TheaterDemo::printTheaterInfo)
                .addCommand("show", ParameterType.INTEGER, theater::printSeatingArrangement)
                .addCommand("book", ParameterType.INTEGER, (Integer hall, Integer row, Integer seat) ->
                        theater.bookSeats(hall, row, seat))
                .addCommand("cancel", ParameterType.INTEGER, (Integer hall, Integer row, Integer seat) ->
                        theater.cancelBooking(hall, row, seat))
                .addCommand("check", ParameterType.INTEGER,(Integer hall, Integer row, Integer seat) ->
                        console.println(theater.isSeatBooked(hall, row, seat) ? "booked" : "free"))
                .addCommand("available", ParameterType.INTEGER, TheaterDemo::printAvailable)
                .addCommand("findBest", ParameterType.INTEGER, TheaterDemo::printBest)
                .addCommand("autoBook",  ParameterType.INTEGER, (Integer hall, Integer seatsNumber) ->
                        theater.autoBook(hall, seatsNumber).ifPresentOrElse(best -> {} , () -> console.println("No place to book")))
                .addExitCommand("exit");

        printHelp();
        builder.build().start();
    }

    public static void printTheaterInfo() {
        System.out.println("Theater info:");
        System.out.println("Number of halls: " + theater.getHalls());
        System.out.println("Number of rows: " + theater.getRows());
        System.out.println("Number of seats: " + theater.getSeats());
    }

    public static void printAvailable(int hall, int row, int seats) {
        if(theater.checkAvailability(hall, row, seats)) {
            System.out.println("Has " + seats + " seats available");
        }else {
            System.out.println("Doesn't have " + seats + " seats available");
        }
    }

    public static void printBest(int hall, int seatsNumber) {
        var best = theater.findBestAvailable(hall, seatsNumber);

        best.ifPresentOrElse(bestPlace -> {
            int seatFrom = bestPlace.getSeat();
            int seatTo = seatFrom + seatsNumber;
            System.out.println("Best places in hall " + hall + " : row " + bestPlace.row + " seats(" + seatFrom + " - " + seatTo + ")");
        }, () -> System.out.println("No best place found to book" + seatsNumber + "seats"));
    }

    public static void printHelp() {
        System.out.println("\nTheater commands:");
        System.out.println("help: show help ");
        System.out.println("info: show theater info (size)");
        System.out.println("show [hall]: prints hall arrangement");
        System.out.println("book [hall] [row] [seat]: books seat");
        System.out.println("cancel [hall] [row] [seat]: cancels booking");
        System.out.println("check [hall] [row] [seat]: checks if seat is booked");
        System.out.println("available [hall] [row] [seats]: checks if row has unbooked seats");
        System.out.println("findBest [hall] [seats]: find best place to book seats");
        System.out.println("autoBook [hall] [seats]: books best seats");
        System.out.println("exit: terminate");
    }
}
