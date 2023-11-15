package com.bondarenko.universityAssigment.lab8.weatherAnalysis;

import com.bondarenko.universityAssigment.lab8.WeatherMeasurement;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAggregatorTest {

    @Test
    void Aggregate_MinTemperatureGroupingById() {
        WeatherAggregator<Integer, Double> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 1, 1, 0, 0, 0),
                new WeatherMeasurement(new Date(), 1, 3, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.min(WeatherMeasurement::getTemperature))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsKey(1));
        assertTrue(actual.containsValue(1d));
    }

    @Test
    void Aggregate_MaxHumidityGroupingByDate() {
        WeatherAggregator<Date, Double> aggregator = new WeatherAggregator<>();
        var date = new Date();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(date, 0, 0, 1, 0, 0),
                new WeatherMeasurement(date, 0, 0, 3, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getDate)
                .aggregating(WeatherAggregators.max(WeatherMeasurement::getHumidity))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsKey(date));
        assertTrue(actual.containsValue(3d));
    }

    @Test
    void Aggregate_AverageWindSpeedGroupingByPrecipitation() {
        WeatherAggregator<Double, Double> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 0, 0, 1, 1),
                new WeatherMeasurement(new Date(), 0, 0, 0, 1, 3)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getPrecipitation)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getWindSpeed))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsKey(1d));
        assertTrue(actual.containsValue(2d));
    }

    @Test
    void Aggregate_CheckConsecutiveAppearance_DataDoesNotSatisfyCondition_ShouldMapToFalse() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 0, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkConsecutiveAppearance(measurement -> measurement.getTemperature() == 20d, 3))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(false));
    }

    @Test
    void Aggregate_CheckConsecutiveAppearance_DataSatisfiesCondition_ShouldMapToTrue() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 0, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 20, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkConsecutiveAppearance(measurement -> measurement.getTemperature() == 20d, 3))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(true));
    }

    @Test
    void Aggregate_CheckTrendAll_DataSatisfiesCondition_ShouldMapToTrue() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 1, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 2, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 3, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 4, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAll((measurement, measurementLater) ->
                        measurement.getTemperature() + 1 == measurementLater.getTemperature(), 1))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(true));
    }

    @Test
    void Aggregate_CheckTrendAll_DataDoesNotSatisfyCondition_ShouldMapToFalse() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 1, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 2, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 3, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 10, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAll((measurement, measurementLater) ->
                        measurement.getTemperature() + 1 == measurementLater.getTemperature(), 1))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(false));
    }

    @Test
    void Aggregate_CheckTrendAny_DataSatisfiesCondition_ShouldMapToTrue() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 1, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 2, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 3, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 10, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAny((measurement, measurementLater) ->
                        measurement.getTemperature() + 2 == measurementLater.getTemperature(), 2))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(true));
    }

    @Test
    void Aggregate_CheckTrendAny_DataDoesNotSatisfyCondition_ShouldMapToFalse() {
        WeatherAggregator<Integer, Boolean> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 0, 1, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 3, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 5, 0, 0, 0),
                new WeatherMeasurement(new Date(), 0, 7, 0, 0, 0)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAny((measurement, measurementLater) ->
                        measurement.getTemperature() + 2 == measurementLater.getTemperature(), 2))
                .aggregate(data);

        assertEquals(1, actual.size());
        assertTrue(actual.containsValue(false));
    }

    @Test
    void Take_ValidDataAndGrouping_ShouldLimitMapSize() {
        WeatherAggregator<Integer, Double> aggregator = new WeatherAggregator<>();
        var expectedSize = 2;
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 1, 1, 1, 1, 1),
                new WeatherMeasurement(new Date(), 2, 2, 2, 2, 2),
                new WeatherMeasurement(new Date(), 3, 3, 3, 3, 3)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getTemperature))
                .take(expectedSize)
                .aggregate(data);

        assertEquals(expectedSize, actual.size());
    }

    @Test
    void SortedBy_ValidDataAndGrouping_ShouldSortByMapValue() {
        var expected = new Double[]{1d, 2d, 3d};
        WeatherAggregator<Integer, Double> aggregator = new WeatherAggregator<>();
        Stream<WeatherMeasurement> data = Stream.of(
                new WeatherMeasurement(new Date(), 1, 1, 1, 1, 1),
                new WeatherMeasurement(new Date(), 3, 3, 3, 3, 3),
                new WeatherMeasurement(new Date(), 2, 2, 2, 2, 2)
        );

        var actual = aggregator
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getTemperature))
                .sortedBy(Double::compare)
                .aggregate(data);

        assertArrayEquals(expected, actual.values().toArray());
    }
}