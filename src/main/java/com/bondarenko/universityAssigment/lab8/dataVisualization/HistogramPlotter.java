package com.bondarenko.universityAssigment.lab8.dataVisualization;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public class HistogramPlotter {
    double minValue;
    double maxValue;
    int height;
    int width;
    char[][] buffer;

    private Function<Object, String> objectNameMapper = Object::toString;

    private static final int COLUMN_WIDTH = 6;

    public void print(Map<?,? extends Number> data, int height) {
        Arrays.stream(plotToString(data, height)).forEach(System.out::println);
    }

    public String[] plotToString(Map<?,? extends Number> data, int height) {
        this.width = data.size() * COLUMN_WIDTH;
        this.height = height;

        this.minValue = data.values().stream().mapToDouble(Number::doubleValue).min().orElse(0);
        this.maxValue = data.values().stream().mapToDouble(Number::doubleValue).max().orElse(Double.MAX_VALUE);

        initializeBuffer(width, height);

        int i = 0;
        for(var entry : data.entrySet()) {
            drawColumn(i++, objectNameMapper.apply(entry.getKey()), entry.getValue().doubleValue());
        }

        String[] stringArray = new String[height];
        for (i = 0; i < height; i++) {
            stringArray[i] = new String(buffer[i]);
        }
        return stringArray;
    }

    public void setObjectNameMapper(Function<Object, String> objectNameMapper) {
        this.objectNameMapper = objectNameMapper;
    }

    private void drawColumn(int index, String caption, Double value) {
        int columnHeight = (int) (height * ((value - minValue) / (maxValue - minValue)));
        if(columnHeight == height) {
            columnHeight--;
        }
        if(columnHeight < 2) {
            columnHeight = 2;
        }

        int x = index * COLUMN_WIDTH;
        drawColumnTop(x, columnHeight);
        drawColumnTrunk(x, columnHeight);
        drawColumnValue(x, columnHeight + 1, value);
        drawColumnCaption(x, caption);
    }

    private void drawColumnTop(int x, int y) {
        if(y <= 0) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            buffer[height-y][x + i + 1] = '_';
        }
    }

    private void drawColumnTrunk(int x, int y) {
        for (int i = y - 1; i > 1; i--) {
            buffer[height-i][x + 1] = '|';
            buffer[height-i][x + 3] = '|';
        }
    }

    private void drawColumnValue(int x, int y, double value) {
        String str = Double.toString(value);

        for (int i = 0; i < COLUMN_WIDTH - 1 && i < str.length(); i++) {
            buffer[height - y][x + i] = str.charAt(i);
        }
    }

    private void drawColumnCaption(int x, String caption) {
        for (int i = 0; i < COLUMN_WIDTH - 1 && i < caption.length(); i++) {
            buffer[height - 1][x + i] = caption.charAt(i);
        }
    }

    private void initializeBuffer(int width, int height) {
        buffer = new char[height][width];
        for(var row : buffer) {
            Arrays.fill(row, ' ');
        }
    }
}
