package com.bondarenko.universityAssigment.lab8.openMeteoAPI;

import com.bondarenko.universityAssigment.lab8.*;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.*;
import java.text.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

public class OpenMeteoAPI {
    private final String API_URL = "https://archive-api.open-meteo.com/v1/archive?";
    private final DecimalFormat coordinatesFormat = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH));
    private final OpenMeteoAPIMapper mapper;
    private final LocationsContainer locationsContainer;


    public OpenMeteoAPI(LocationsContainer locationsContainer) {
        this.locationsContainer = locationsContainer;
        mapper = new OpenMeteoAPIMapper(locationsContainer);
    }

    @SneakyThrows
    public Stream<WeatherMeasurement> getDailyMeasurements(LocalDate from, LocalDate to) {
        String requestUrl = API_URL + buildLocationsQueryParam() + "&" + buildDatesQueryParam(from, to) + "&" + getDataFormatQueryParam();
        URI requestUri = new URI(requestUrl);

        HttpClient http = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestUri)
                .GET()
                .build();

        response = http.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.mapJsonToWeatherMeasurements(response.body());
    }

    private String buildLocationsQueryParam() {
        String latitudes = locationsContainer.getLocations()
                .map(location -> coordinatesFormat.format(location.getLatitude()))
                .collect(Collectors.joining(","));

        String longitudes = locationsContainer.getLocations()
                .map(location -> coordinatesFormat.format(location.getLongitude()))
                .collect(Collectors.joining(","));

        return "latitude=" + latitudes + "&longitude=" + longitudes;
    }

    private String buildDatesQueryParam(LocalDate from, LocalDate to) {
        return "start_date=" + from.toString() + "&end_date=" + to.toString();
    }

    private String getDataFormatQueryParam(){
        return "hourly=relative_humidity_2m&daily=temperature_2m_mean,precipitation_sum,wind_speed_10m_max&timeformat=unixtime";
    }
}
