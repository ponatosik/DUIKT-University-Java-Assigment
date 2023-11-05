package com.bondarenko.universityAssigment.lab8;

import lombok.Value;

import java.util.Date;

@Value
public class WeatherMeasurement {
    Date date;
    int locationId;
    double temperature; // In celsius
    double humidity; // In percents (0 - 100%)
    double precipitation; // In millimeters
    double windSpeed; // In meters per second
}
