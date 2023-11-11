package com.bondarenko.universityAssigment.lab8.weatherAnalysis;

import com.bondarenko.universityAssigment.lab8.WeatherMeasurement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeatherAggregator<T, R> {
    private Function<WeatherMeasurement, T> groupingFunction;
    private Function<List<WeatherMeasurement>, R> aggregationFunction;
    private Comparator<R> sortComparator;
    private Integer limit;

    public WeatherAggregator() {
        groupingFunction = measurement -> null;
        aggregationFunction = list -> null;
        sortComparator = null;
        limit = null;
    }

    public WeatherAggregator<T, R> groupingBy(Function<WeatherMeasurement, T> groupingFunction) {
        this.groupingFunction = groupingFunction;
        return this;
    }

    public WeatherAggregator<T, R> aggregating(Function<List<WeatherMeasurement>, R> aggregationFunction) {
        this.aggregationFunction = aggregationFunction;
        return this;
    }

    public WeatherAggregator<T, R> sortedBy(Comparator<R> sortComparator) {
        this.sortComparator = sortComparator;
        return this;
    }

    public WeatherAggregator<T, R> take(int limit) {
        this.limit = limit;
        return this;
    }

    public Map<T, R> aggregate(Stream<WeatherMeasurement> measurements) {
        var entries = measurements.collect(Collectors.groupingBy(groupingFunction))
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), aggregationFunction.apply(entry.getValue())));

        if(sortComparator != null){
            entries = entries.sorted((entry1, entry2) -> sortComparator.compare(entry1.getValue(), entry2.getValue()));
        }
        if (limit != null) {
            entries = entries.limit(limit);
        }

        return entries.collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
    }
}
