package com.bondarenko.universityAssigment.lab6;

import java.util.Arrays;

public class Theater {
    private final int halls;
    private final int rows;
    private final int seats;

    private final int[][][] theaterData;

    public Theater(int halls, int rows, int seats) {
        this.halls = halls;
        this.rows = rows;
        this.seats = seats;

        theaterData = new int[halls][rows][seats];
    }

    public void bookSeats(int hallNumber, int row, int... seats){
        int[] rowData = getRowData(hallNumber, row);

        for (int seat : seats) {
            rowData[seat] = 1;
        }
    }

    public void cancelBooking(int hallNumber, int row, int... seats){
        int[] rowData = getRowData(hallNumber, row);

        for (int seat : seats) {
            rowData[seat] = 0;
        }
    }

    public boolean isSeatBooked(int hallNumber, int row, int seat) {
        return theaterData[hallNumber][row][seat] == 1;
    }

    public boolean checkAvailability(int hallNumber, int row, int numSeats){
        int[] rowData = getRowData(hallNumber, row);
        int consecutiveCounter = 0;

        for (int i = 0; i < seats; i++) {
            consecutiveCounter = rowData[i] == 0 ? consecutiveCounter + 1 : 0;
            if(consecutiveCounter == numSeats){
                return true;
            }
        }

        return false;
    }

    private int[][] getHallData(int hall){
        return theaterData[hall];
    }

    private int[] getRowData(int hall, int row){
        return theaterData[hall][row];
    }
}
