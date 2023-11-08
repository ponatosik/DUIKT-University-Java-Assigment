package com.bondarenko.universityAssigment.lab8.openMeteoAPI;

import com.bondarenko.universityAssigment.lab8.WeatherMeasurement;
import com.bondarenko.universityAssigment.lab8.WeatherMeasurementLocation;
import com.bondarenko.universityAssigment.lab8.WeatherMeasurementLocationsContainer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lombok.SneakyThrows;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.*;


public class OpenMeteoAPIMapper {
    private final WeatherMeasurementLocationsContainer locationsContainer;

    public OpenMeteoAPIMapper(WeatherMeasurementLocationsContainer locationsContainer) {
        this.locationsContainer = locationsContainer;
    }

    public Stream<WeatherMeasurement> mapJsonToWeatherMeasurements(String jsonResponse) {
        JsonToken firstToken = readFirstJsonToken(jsonResponse);
        if(firstToken == JsonToken.BEGIN_ARRAY) {
            Type collectionType = new TypeToken<List<OpenMeteoDTO>>() { }.getType();
            Collection<OpenMeteoDTO> weatherDTOs = new Gson().fromJson(jsonResponse, collectionType);

            return weatherDTOs.stream().flatMap(this::mapDTOToWeatherMeasurements);
        } else if(firstToken == JsonToken.BEGIN_OBJECT) {
            OpenMeteoDTO weatherDTO = new Gson().fromJson(jsonResponse, OpenMeteoDTO.class);

            return mapDTOToWeatherMeasurements(weatherDTO);
        }
        return Stream.empty();
    }

    public Stream<WeatherMeasurement> mapDTOToWeatherMeasurements(OpenMeteoDTO dto) {
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        Optional<WeatherMeasurementLocation> location = locationsContainer.getLocationByCoordinates(latitude, longitude);
        int id = location.map(WeatherMeasurementLocation::getLocationId).orElse(0);

        Date[] dates = dto.getDaily().getTime();
        Double[] temperatures = dto.getDaily().getTemperature_2m_mean();
        Double[] humidities = aggregateHourlyMeasurementsToDailyHumidity(dto.getHourly());
        Double[] precipitation = dto.getDaily().getPrecipitation_sum();
        Double[] windSpeeds = dto.getDaily().getWind_speed_10m_max();

        return IntStream.range(0, minArraySize(dates, temperatures, humidities, precipitation, windSpeeds))
                .mapToObj(i -> new WeatherMeasurement(
                        dates[i],
                        id,
                        temperatures[i],
                        humidities[i],
                        precipitation[i],
                        windSpeeds[i]
                ));
    }

    @SneakyThrows
    private JsonToken readFirstJsonToken(String jsonResponse) {
        JsonReader reader = new JsonReader(new StringReader(jsonResponse));
        return reader.peek();
    }


    private Double[] aggregateHourlyMeasurementsToDailyHumidity(OpenMeteoDTO.HourlyMeasurments hourlyMeasurments) {
        Double[] humidities =  hourlyMeasurments.getRelative_humidity_2m();
        Date[] dates =  hourlyMeasurments.getTime();

        // TODO: find more optimal way to aggregate data

        return IntStream.range(0, Math.min(humidities.length, dates.length))
                .mapToObj(i -> Map.entry(dateWithoutTime(dates[i]), humidities[i]))
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .values()
                .stream()
                .map(list -> list.stream()
                        .reduce(0d, (sum, entry) -> entry.getValue() + sum, Double::sum) / list.size())
                .toArray(Double[]::new);
    }

    private Date dateWithoutTime(Date date){
        return new Date(date.toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate().toEpochDay());
    }

    private int minArraySize(Object[] ... arrays) {
        return Arrays.stream(arrays).map(array -> array.length).min(Integer::compare).orElse(0);
    }
}
