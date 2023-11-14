package com.bondarenko.universityAssigment.lab8;

import com.bondarenko.universityAssigment.lab8.dataVisualization.HistogramPlotter;
import com.bondarenko.universityAssigment.lab8.openMeteoAPI.OpenMeteoAPI;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregator;
import com.bondarenko.universityAssigment.lab8.weatherAnalysis.WeatherAggregators;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WeatherDemo {

    private static LocalDate DataFrom = LocalDate.now().minusDays(15).minusMonths(12);
    private static LocalDate DataTo = LocalDate.now().minusDays(15);;

    private static HistogramPlotter histogram = new HistogramPlotter();

    private static LocationsContainer locations = new LocationsContainer(List.of(
            new MeasurementLocation(1, 60.1, 60.2),
            new MeasurementLocation(2, 15.1, 72.),
            new MeasurementLocation(3, 46.5, 35.2),
            new MeasurementLocation(4, 64.9, 92.2),
            new MeasurementLocation(5, 34.4, 29.2),
            new MeasurementLocation(6, 45.2, 85.2),
            new MeasurementLocation(7, 38.7, 23.2),
            new MeasurementLocation(8, 21.6, 89.2),
            new MeasurementLocation(9, 89.2, 42.2),
            new MeasurementLocation(10, 60.8, 26.2),
            new MeasurementLocation(11, 31.3, 62.2),
            new MeasurementLocation(12, 65.2, 34.2),
            new MeasurementLocation(13, 28.9, 39.2),
            new MeasurementLocation(14, 63.3, 22.2),
            new MeasurementLocation(15, 82, 12.2)
    ));

    public static void main(String[] args) {
        OpenMeteoAPI api = new OpenMeteoAPI(locations);

        System.out.println("Fetching weather date from OpenMeteoAPI");
        List<WeatherMeasurement> measurements = api.getDailyMeasurements(DataFrom, DataTo).toList();

        Map<Integer, Double> coldestById = new WeatherAggregator<Integer, Double>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.min(WeatherMeasurement::getTemperature))
                .sortedBy(Double::compare)
                .take(10)
                .aggregate(measurements.stream());

        Map<Integer, Double> hottestById = new WeatherAggregator<Integer, Double>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.max(WeatherMeasurement::getTemperature))
                .sortedBy((temp1, temp2) -> Double.compare(temp2, temp1))
                .take(10)
                .aggregate(measurements.stream());

        Map<Integer, Double> mostHumidityById = new WeatherAggregator<Integer, Double>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.sum(WeatherMeasurement::getPrecipitation))
                .sortedBy(Double::compare)
                .take(10)
                .aggregate(measurements.stream());

        Map<Integer, Boolean> have5dayWithRainById = new WeatherAggregator<Integer, Boolean>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkConsecutiveAppearance(weather -> weather.getPrecipitation() > 0, 5))
                .aggregate(measurements.stream());

        Map<Integer, Boolean> have5dayWithRaisingTempById = new WeatherAggregator<Integer, Boolean>()
                .groupingBy(WeatherMeasurement::getLocationId)
                .aggregating(WeatherAggregators.checkTrendAny(
                        (weather, weatherLater) -> weatherLater.getTemperature() >= weather.getTemperature() + 5, 5))
                .aggregate(measurements.stream());

        Map<Integer, Double> averageTempByMonth = new WeatherAggregator<Integer, Double>()
                .groupingBy(weather -> weather.getDate().getMonth() + 1)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getTemperature))
                .aggregate(measurements.stream());

        Map<Integer, Double> averageHumidityByMonth = new WeatherAggregator<Integer, Double>()
                .groupingBy(weather -> weather.getDate().getMonth() + 1)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getHumidity))
                .aggregate(measurements.stream());

        Map<Integer, Double> averagePrecipitationByMonth = new WeatherAggregator<Integer, Double>()
                .groupingBy(weather -> weather.getDate().getMonth() + 1)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getPrecipitation))
                .aggregate(measurements.stream());

        Map<Integer, Double> averageWindSpeedByMonth = new WeatherAggregator<Integer, Double>()
                .groupingBy(weather -> weather.getDate().getMonth() + 1)
                .aggregating(WeatherAggregators.average(WeatherMeasurement::getWindSpeed))
                .aggregate(measurements.stream());


        System.out.println("\n\n\n10 Coldest stations (C) by station id");
        histogram.print(coldestById, 15);

        System.out.println("\n\n\n10 Hottest stations (C) by station id");
        histogram.print(hottestById, 15);

        System.out.println("\n\n\n10 Wettest stations (precipitations sum mm) by station id");
        histogram.print(mostHumidityById, 15);

        System.out.println("\n\n\nStations where 5 days with any precipitations found:");
        have5dayWithRainById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(entry.getKey()));

        System.out.println("\n\n\nStations where temperature rising by 5C in 5 days found:");
        have5dayWithRaisingTempById.entrySet().stream().filter(Map.Entry::getValue)
                .forEach(entry-> System.out.println(entry.getKey()));

        System.out.println("\n\n\nGlobal average temperature (C) by months");
        histogram.print(averageTempByMonth, 15);

        System.out.println("\n\n\nGlobal average humidity (%) by months");
        histogram.print(averageHumidityByMonth, 15);

        System.out.println("\n\n\nGlobal average precipitation per day (mm) by months");
        histogram.print(averagePrecipitationByMonth, 15);

        System.out.println("\n\n\nMonth with fastest average wind speed:");
        averageWindSpeedByMonth.entrySet().stream().max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(entry.getKey() + "th month: " + entry.getValue() + "m/s"));
    }
}
