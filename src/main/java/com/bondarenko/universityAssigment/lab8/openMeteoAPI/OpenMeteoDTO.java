package com.bondarenko.universityAssigment.lab8.openMeteoAPI;

import lombok.Value;

import java.util.Arrays;
import java.util.Date;

@Value
public class OpenMeteoDTO {
    @Value
    public static class HourlyMeasurments {
        Long[] time; // unix timestamp time
        Double[] relative_humidity_2m;

        public Date[] getTime() {
            return Arrays.stream(time).map(timestamp -> new Date(timestamp * 1000)).toArray(Date[]::new);
        }
    }

    @Value
    public static class DailyMeasurments {
        Long[] time; // unix timestamp time
        Double[] temperature_2m_mean;
        Double[] precipitation_sum;
        Double[] wind_speed_10m_max;

        public Date[] getTime() {
            return Arrays.stream(time).map(timestamp -> new Date(timestamp * 1000)).toArray(Date[]::new);
        }
    }

    Double latitude;
    Double longitude;
    HourlyMeasurments hourly;
    DailyMeasurments daily;
}
