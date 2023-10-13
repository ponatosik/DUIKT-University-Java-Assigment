package com.bondarenko.universityAssigment.lab6;

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


    private int[][] getHallData(int hall){
        return theaterData[hall];
    }

    private int[] getRowData(int hall, int row){
        return theaterData[hall][row];
    }
}
