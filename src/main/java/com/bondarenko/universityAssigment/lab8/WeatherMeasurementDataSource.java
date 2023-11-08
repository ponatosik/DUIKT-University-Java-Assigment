package com.bondarenko.universityAssigment.lab8;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface WeatherMeasurementDataSource {
    Stream<WeatherMeasurement> getDailyMeasurements(LocalDate from, LocalDate to);
}
