package com.bondarenko.universityAssigment.lab8.weatherAnalysis;

import com.bondarenko.universityAssigment.lab8.WeatherMeasurement;

import java.util.List;
import java.util.function.*;
import java.util.stream.IntStream;

public class WeatherAggregators {
    public static Function<List<WeatherMeasurement>, Double> min(ToDoubleFunction<WeatherMeasurement> attributeMapper) {
        return measurements -> measurements.stream().mapToDouble(attributeMapper).min().orElseThrow(NullPointerException::new);
    }

    public static Function<List<WeatherMeasurement>, Double> max(ToDoubleFunction<WeatherMeasurement> attributeMapper) {
        return measurements -> measurements.stream().mapToDouble(attributeMapper).max().orElseThrow(NullPointerException::new);
    }

    public static Function<List<WeatherMeasurement>, Double> average(ToDoubleFunction<WeatherMeasurement> attributeMapper) {
        return measurements -> measurements.stream().mapToDouble(attributeMapper).average().orElseThrow(NullPointerException::new);
    }

    public static Function<List<WeatherMeasurement>, Boolean> checkConsecutiveAppearance(Predicate<WeatherMeasurement> predicate, int number) {
        return measurements -> measurements.stream()
                .map(measurement -> predicate.test(measurement) ? 1 : 0)
                .reduce(0, (counter, measurementBool) -> counter >= number ? counter : (counter + 1) * measurementBool)
                >= number;
    }

    public static Function<List<WeatherMeasurement>, Boolean> checkTrendAny(BiPredicate<WeatherMeasurement, WeatherMeasurement> predicate, int timeSpan) {
        return measurements -> IntStream.range(0, measurements.size() - timeSpan)
                .mapToObj(i ->
                        predicate.test(measurements.get(i), measurements.get(i + timeSpan)))
                .anyMatch(Boolean::booleanValue);
    }

    public static Function<List<WeatherMeasurement>, Boolean> checkTrendAll(BiPredicate<WeatherMeasurement, WeatherMeasurement> predicate, int timeSpan) {
        return measurements -> IntStream.range(0, measurements.size() - timeSpan)
                .mapToObj(i ->
                        predicate.test(measurements.get(i), measurements.get(i + timeSpan)))
                .allMatch(Boolean::booleanValue);
    }
}
