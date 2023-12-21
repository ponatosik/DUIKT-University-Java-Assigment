package com.bondarenko.universityAssigment.lab6;

import com.bondarenko.universityAssigment.lab6.CommandLine.CommandLineCommand.ParameterType;
import com.bondarenko.universityAssigment.lab6.CommandLine.CommandLineInterfaceBuilder;
import com.bondarenko.universityAssigment.lab6.Theater.Implementation.SafeTheater;
import com.bondarenko.universityAssigment.lab6.Theater.Theater;

import java.io.PrintStream;
import java.util.List;
import java.util.function.Consumer;

public class TheaterDemo {
    private static final Theater theater = new SafeTheater(5, 10, 20);

    public static void main(String[] args) {
        CommandLineInterfaceBuilder builder = new CommandLineInterfaceBuilder();
        PrintStream console = System.out;

        builder.addCommandWithoutParameter("help", TheaterDemo::printHelp)
                .addCommandWithoutParameter("info", TheaterDemo::printTheaterInfo)
                .addCommandWithParameter("show", ParameterType.INTEGER, theater::printSeatingArrangement)
                .addCommandWithParameters("book", ParameterType.INTEGER, 3, (Consumer<List<Integer>>) indexes ->
                        theater.bookSeats(indexes.get(0), indexes.get(1), indexes.get(2)))
                .addCommandWithParameters("cancel", ParameterType.INTEGER, 3, (Consumer<List<Integer>>) indexes ->
                        theater.cancelBooking(indexes.get(0), indexes.get(1), indexes.get(2)))
                .addCommandWithParameters("check", ParameterType.INTEGER, 3, (Consumer<List<Integer>>) indexes ->
                        console.println(theater.isSeatBooked(indexes.get(0), indexes.get(1), indexes.get(2)) ? "booked" : "free"))
                .addCommandWithParameters("available", ParameterType.INTEGER, 3, TheaterDemo::printAvailable)
                .addCommandWithParameters("findBest", ParameterType.INTEGER, 2, TheaterDemo::printBest)
                .addCommandWithParameters("autoBook",  ParameterType.INTEGER, 2, (Consumer<List<Integer>>) indexes ->
                        theater.autoBook(indexes.get(0), indexes.get(1)).ifPresentOrElse( best->{} , () -> console.println("No place to book")))
                .addExitCommand("exit");

        builder.build().start();
    }

    public static void test(List<Integer> params)
    {
        theater.bookSeats(params.get(0), params.get(1), params.get(2));
    }

    public static void printTheaterInfo() {
        System.out.println("Theater info:");
        System.out.println("Number of halls: " + theater.getHalls());
        System.out.println("Number of rows: " + theater.getRows());
        System.out.println("Number of seats: " + theater.getSeats());
    }

    public static void printAvailable(List<Integer> indexes) {
        int seats = indexes.get(2);
        if(theater.checkAvailability(indexes.get(0), indexes.get(1), seats)) {
            System.out.println("Has " + seats + " seats available");
        }else {
            System.out.println("Doesn't have " + seats + " seats available");
        }
    }

    public static void printBest(List<Integer> indexes) {
        int hall = indexes.get(0);
        int number = indexes.get(1);

        var best = theater.findBestAvailable(hall, number);

        best.ifPresentOrElse(bestPlace -> {
            int seatFrom = bestPlace.getSeat();
            int seatTo = seatFrom + number;
            System.out.println("Best places in hall " + hall + " : row " + bestPlace.row + " seats(" + seatFrom + " - " + seatTo + ")");
        }, () -> System.out.println("No best place found to book" + number + "seats"));
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
