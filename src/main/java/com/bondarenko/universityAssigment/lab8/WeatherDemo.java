package com.bondarenko.universityAssigment.lab8;

import com.bondarenko.universityAssigment.lab8.dataVisualization.HistogramPlotter;
import com.bondarenko.universityAssigment.lab8.openMeteoAPI.OpenMeteoAPI;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.NumericWeatherAggregatorFactory;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregator;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class WeatherDemo {

    private static LocalDate DataFrom = LocalDate.now().minusDays(15).minusMonths(12);
    private static LocalDate DataTo = LocalDate.now().minusDays(15);

    private static final HistogramPlotter histogram = new HistogramPlotter();
    private static final NumericWeatherAggregatorFactory aggregators = new NumericWeatherAggregatorFactory();

    // A bunch of random locations
    private static LocationsContainer locations = new LocationsContainer(List.of(
            new MeasurementLocation(1, 60.1, -10.2),
            new MeasurementLocation(2, -15.1, -72.),
            new MeasurementLocation(3, 46.5, -35.2),
            new MeasurementLocation(4, 64.9, 12.2),
            new MeasurementLocation(5, -34.4, -29.2),
            new MeasurementLocation(6, 45.2, 5.2),
            new MeasurementLocation(7, -38.7, -23.2),
            new MeasurementLocation(8, 21.6, 9.2),
            new MeasurementLocation(9, 9.2, -42.2),
            new MeasurementLocation(10, -60.8, 26.2),
            new MeasurementLocation(11, -31.3, -22.2),
            new MeasurementLocation(12, 5.2, 34.2),
            new MeasurementLocation(13, -28.9, 39.2),
            new MeasurementLocation(14, 13.3, -22.2),
            new MeasurementLocation(15, -12.5, 12.2),
            new MeasurementLocation(16, 50.2, -40.2),
            new MeasurementLocation(17, 16.6, 10.2),
            new MeasurementLocation(18, 1, -2)
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
                .aggregating(WeatherAggregators.checkConsecutiveAppearance(weather -> weather.getPrecipitation() > 0, 5))
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

        System.out.println("\n\n\n10 Coldest stations (min temperature C) by station id");
        histogram.print(coldestById, 15);

        System.out.println("\n\n\n10 Hottest stations (max temperature C) by station id");
        histogram.print(hottestById, 15);

        System.out.println("\n\n\n10 Wettest stations (precipitations sum mm) by station id");
        histogram.print(mostHumidityById, 15);

        System.out.println("\n\n\nStations where 5 days in a row with any precipitations found:");
        have5dayWithRainById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(entry.getKey()));

        System.out.println("\n\n\nStations where temperature rising by 5C in 5 days found:");
        have5dayWithRaisingTempById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(entry.getKey()));

        System.out.println("\n\n\nGlobal average temperature (C) by months");
        histogram.print(averageTempByMonth, 20);

        System.out.println("\n\n\nGlobal average humidity (%) by months");
        histogram.print(averageHumidityByMonth, 20);

        System.out.println("\n\n\nGlobal average precipitation per day (mm) by months");
        histogram.print(averagePrecipitationByMonth, 20);

        System.out.println("\n\n\nMonth with fastest average wind speed:");
        averageWindSpeedByMonth.entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(entry.getKey() + "th month: " + entry.getValue() + "m/s"));
    }
}