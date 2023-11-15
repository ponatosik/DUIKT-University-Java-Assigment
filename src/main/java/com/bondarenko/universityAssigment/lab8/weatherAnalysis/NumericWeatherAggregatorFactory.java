package com.bondarenko.universityAssigment.lab8.weatherAnalysis;

import com.bondarenko.universityAssigment.lab8.WeatherMeasurement;

import java.util.*;
import java.util.function.*;
import java.util.regex.*;

public class NumericWeatherAggregatorFactory {
    private final static String PATTERN_STR = "(?<groupBy>\\w+):(?<aggregator>\\w+)\\((?<aggregate>\\w+)\\)\\*?(?<limit>\\d+)?(?<sort>\\w+)?";
    private final static Pattern PATTERN = Pattern.compile(PATTERN_STR, Pattern.CASE_INSENSITIVE);

    public WeatherAggregator<Number, Double> getAggregator(String aggregatorExpression) {
        String str = aggregatorExpression.toLowerCase();
        Matcher matcher = PATTERN.matcher(str);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid WeatherAggregatorFactory syntax(" + str + ")");
        }

        String groupByStr = matcher.group("groupBy");
        String aggregatorStr = matcher.group("aggregator");
        String aggregateStr = matcher.group("aggregate");
        String limitStr = matcher.group("limit");
        String sortStr = matcher.group("sort");

        WeatherAggregator<Number, Double> aggregator = new WeatherAggregator<>();
        aggregator.groupingBy(parseGroupByFunction(groupByStr));
        aggregator.aggregating(parseAggregator(aggregatorStr, aggregateStr));

        if (limitStr != null) {
            aggregator.take(parseLimit(limitStr));
        }
        if (sortStr != null) {
            aggregator.sortedBy(parseSortFunction(sortStr));
        }

        return aggregator;
    }

    private Function<WeatherMeasurement, Number> parseGroupByFunction(String str) {
        return switch (str) {
            case "locationid", "location", "loc", "id" -> WeatherMeasurement::getLocationId;
            case "date", "time", "datetime" -> measurement -> measurement.getDate().getTime();
            case "month", "datemonth" -> measurement -> measurement.getDate().getMonth() + 1;
            case "year", "dateyear" -> measurement -> measurement.getDate().getYear() + 1900;
            default -> null;
        };
    }

    private Function<List<WeatherMeasurement>, Double> parseAggregator(String aggregatorStr, String aggregateStr) {
        ToDoubleFunction<WeatherMeasurement> aggregateBy = switch (aggregateStr) {
            case "temperature", "temp" -> WeatherMeasurement::getTemperature;
            case "precipitation", "prec" -> WeatherMeasurement::getPrecipitation;
            case "humidity", "hum" -> WeatherMeasurement::getHumidity;
            case "windspeed", "wind" -> WeatherMeasurement::getWindSpeed;
            default -> null;
        };

        return switch (aggregatorStr) {
            case "minimum", "min" -> WeatherAggregators.min(aggregateBy);
            case "maximum", "max" -> WeatherAggregators.max(aggregateBy);
            case "average", "avr" -> WeatherAggregators.average(aggregateBy);
            case "sum" -> WeatherAggregators.sum(aggregateBy);
            default -> null;
        };
    }


    private Comparator<Double> parseSortFunction(String str) {
        return switch (str) {
            case "ascending", "asc" -> Double::compare;
            case "descending", "desc" -> ((Comparator<Double>) Double::compare).reversed();
            case "reverseorder", "reversed", "reverse" -> Comparator.reverseOrder();
            default -> null;
        };
    }

    private int parseLimit(String str) {
        return Integer.parseInt(str);
    }
}
