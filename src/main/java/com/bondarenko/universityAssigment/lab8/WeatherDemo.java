package com.bondarenko.universityAssigment.lab8;

import com.bondarenko.universityAssigment.lab8.dataVisualization.HistogramPlotter;
import com.bondarenko.universityAssigment.lab8.openMeteoAPI.OpenMeteoAPI;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.NumericWeatherAggregatorFactory;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregator;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregators;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherDemo {

    private static LocalDate DataFrom = LocalDate.now().minusDays(15).minusMonths(12);
    private static LocalDate DataTo = LocalDate.now().minusDays(15);

    private static final HistogramPlotter histogram = new HistogramPlotter();
    private static final NumericWeatherAggregatorFactory aggregators = new NumericWeatherAggregatorFactory();

    private static LocationsContainer locations = new LocationsContainer(List.of(
            new MeasurementLocation(1, 50.4, 30.5, "Kyiv"),
            new MeasurementLocation(2, 40.7, -74.0, "NYC"),
            new MeasurementLocation(3, 51.5, -0.1, "London"),
            new MeasurementLocation(4, 52.2, 21, "Warsaw"),
            new MeasurementLocation(5, 35, -120, "LA"),
            new MeasurementLocation(6, 50, 15, "Viena"),
            new MeasurementLocation(7, 35, 140, "Tokyo"),
            new MeasurementLocation(8, 35, 135, "Kyoto"),
            new MeasurementLocation(9, 45, 30, "Odesa"),
            new MeasurementLocation(10, 55, 25, "Riga"),
            new MeasurementLocation(11, 45, 25, "Sofia"),
            new MeasurementLocation(12, 50, 25, "Lviv"),
            new MeasurementLocation(13, 40, 15, "Rome"),
            new MeasurementLocation(14, 45, 5, "Monaco"),
            new MeasurementLocation(15, 50, 0, "Paris"),
            new MeasurementLocation(16, 50, -5, "Brest"),
            new MeasurementLocation(17, 25, -80, "Miami"),
            new MeasurementLocation(18, 40, 50, "Baku")
    ));

    public static void main(String[] args) {

        // Requesting OpenMeteoAPI

        OpenMeteoAPI api = new OpenMeteoAPI(locations);

        System.out.println("Fetching weather data from OpenMeteoAPI...");
        List<WeatherMeasurement> measurements = api.getDailyMeasurements(DataFrom, DataTo).toList();

        // Analyzing data

        Map<Number, Double> coldestById, hottestById, mostHumidityById;
        Map<Integer, Boolean> have5dayWithRainById, have5dayWithRaisingTempById;
        Map<Number, Double> averageTempByMonth, averageHumidityByMonth, averagePrecipitationByMonth, averageWindSpeedByMonth;

        coldestById = aggregators.getAggregator("location:min(temperature)*10asc").aggregate(measurements.stream());
        hottestById = aggregators.getAggregator("location:max(temperature)*10desc").aggregate(measurements.stream());
        mostHumidityById = aggregators.getAggregator("location:sum(humidity)*10desc").aggregate(measurements.stream());

        have5dayWithRainById = new WeatherAggregator<Integer, Boolean>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkConsecutiveAppearance(weather -> weather.getPrecipitation() > 1, 5))
                .aggregate(measurements.stream());

        have5dayWithRaisingTempById = new WeatherAggregator<Integer, Boolean>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAny(
                        (weather, weatherLater) -> weatherLater.getTemperature() >= weather.getTemperature() + 5, 5))
                .aggregate(measurements.stream());

        averageTempByMonth = aggregators.getAggregator("month:avr(temperature)").aggregate(measurements.stream());
        averageHumidityByMonth = aggregators.getAggregator("month:avr(humidity)").aggregate(measurements.stream());
        averagePrecipitationByMonth = aggregators.getAggregator("month:avr(precipitation)").aggregate(measurements.stream());
        averageWindSpeedByMonth = aggregators.getAggregator("month:avr(windSpeed)").aggregate(measurements.stream());

        // Printing data

        histogram.setObjectNameMapper(locationId -> getLocationName((Number) locationId));

        System.out.println("\n\n\n10 Coldest stations (min mean temperature C)");
        histogram.print(coldestById, 15);

        System.out.println("\n\n\n10 Hottest stations (max mean temperature C)");
        histogram.print(hottestById, 15);

        System.out.println("\n\n\n10 Wettest stations (precipitations sum mm)");
        histogram.print(mostHumidityById, 15);

        System.out.println("\n\n\nStations where 5 days in a row with any precipitations found:");
        have5dayWithRainById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(getLocationName(entry.getKey())));

        System.out.println("\n\n\nStations where temperature rising by 5C in 5 days found:");
        have5dayWithRaisingTempById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(getLocationName(entry.getKey())));

        histogram.setObjectNameMapper(month -> getMonthName((Number) month));

        System.out.println("\n\n\nGlobal average temperature (C) by months");
        histogram.print(averageTempByMonth, 20);

        System.out.println("\n\n\nGlobal average humidity (%) by months");
        histogram.print(averageHumidityByMonth, 20);

        System.out.println("\n\n\nGlobal average precipitation per day (mm) by months");
        histogram.print(averagePrecipitationByMonth, 20);

        System.out.println("\n\n\nMonth with fastest average wind speed:");
        averageWindSpeedByMonth.entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(getMonthName(entry.getKey()) + ": " + entry.getValue() + "m/s"));
    }

    private static String getMonthName(Number month) {
        return LocalDate.of(0, month.intValue(), 1).getMonth().getDisplayName(TextStyle.SHORT, Locale.US);
    }

    private static String getLocationName(Number locatonId) {
        return locations.getLocationById(locatonId.intValue()).map(MeasurementLocation::getName).orElse("unknown location");
    }
}