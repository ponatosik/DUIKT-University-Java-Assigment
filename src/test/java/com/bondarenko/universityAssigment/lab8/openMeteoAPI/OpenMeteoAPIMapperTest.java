package com.bondarenko.universityAssigment.lab8.openMeteoAPI;

import com.bondarenko.universityAssigment.lab8.LocationsContainer;
import com.bondarenko.universityAssigment.lab8.MeasurementLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenMeteoAPIMapperTest {

    OpenMeteoAPIMapper mapper;
    MeasurementLocation location =  new MeasurementLocation(1,0,0);

    @BeforeEach
    void setUp() {
        LocationsContainer locationsMock = mock(LocationsContainer.class);
        when(locationsMock.getLocationByCoordinates(anyDouble(), anyDouble())).thenReturn(Optional.of(location));
        when(locationsMock.getLocationByCoordinatesExact(anyDouble(), anyDouble())).thenReturn(Optional.of(location));
        when(locationsMock.getLocationByCoordinatesClosest(anyDouble(), anyDouble())).thenReturn(Optional.of(location));
        mapper = new OpenMeteoAPIMapper(locationsMock);
    }

    @Test
    void MapJsonToWeatherMeasurements_SingleMeasurement_ShouldReturnMappedMeasurement() {
        var expectedLocation = location.getLocationId();
        var expectedTemperature = 8.8d;
        var expectedHumidity = 96;
        var expectedPrecipitation = 0.1;
        var expectedWindSpeed = 23.4;
        String json = """
                {
                  "latitude": 50.08787,
                  "longitude": 13.059441,
                  "hourly": {
                    "time": [1698102000],
                    "relative_humidity_2m": [96]
                  },
                  "daily": {
                    "time": [1698019200],
                    "temperature_2m_mean": [8.8],
                    "precipitation_sum": [0.1],
                    "wind_speed_10m_max": [23.4]
                  }
                }
                """;

        var actual = mapper.mapJsonToWeatherMeasurements(json).toList();

        assertFalse(actual.isEmpty());
        assertEquals(1, actual.size());
        assertEquals(expectedLocation, actual.get(0).getLocationId());
        assertEquals(expectedTemperature, actual.get(0).getTemperature());
        assertEquals(expectedHumidity, actual.get(0).getHumidity());
        assertEquals(expectedPrecipitation, actual.get(0).getPrecipitation());
        assertEquals(expectedWindSpeed, actual.get(0).getWindSpeed());
    }

    @Test
    void MapJsonToWeatherMeasurements_MultipleLocations_ShouldReturnMappedMeasurement() {
        var expectedTemperature = 8.8d;
        var expectedHumidity = 96;
        var expectedPrecipitation = 0.1;
        var expectedWindSpeed = 23.4;

        var expectedTemperature2 = 10d;
        var expectedHumidity2 = 10;
        var expectedPrecipitation2 = 10;
        var expectedWindSpeed2 = 10;
        String json = """
                [
                  {
                    "latitude": 50.08787,
                    "longitude": 13.059441,
                    "hourly": {
                      "time": [
                        1698102000
                      ],
                      "relative_humidity_2m": [
                        96
                      ]
                    },
                    "daily": {
                      "time": [
                        1698019200
                      ],
                      "temperature_2m_mean": [
                        8.8
                      ],
                      "precipitation_sum": [
                        0.1
                      ],
                      "wind_speed_10m_max": [
                        23.4
                      ]
                    }
                  },
                  {
                    "latitude": 10.08787,
                    "longitude": 10.059441,
                    "hourly": {
                      "time": [
                        1698102000
                      ],
                      "relative_humidity_2m": [
                        10
                      ]
                    },
                    "daily": {
                      "time": [
                        1698019200
                      ],
                      "temperature_2m_mean": [
                        10
                      ],
                      "precipitation_sum": [
                        10
                      ],
                      "wind_speed_10m_max": [
                        10
                      ]
                    }
                  }
                ]
                """;

        var actual = mapper.mapJsonToWeatherMeasurements(json).toList();

        assertFalse(actual.isEmpty());
        assertEquals(2, actual.size());
        assertEquals(expectedTemperature, actual.get(0).getTemperature());
        assertEquals(expectedHumidity, actual.get(0).getHumidity());
        assertEquals(expectedPrecipitation, actual.get(0).getPrecipitation());
        assertEquals(expectedWindSpeed, actual.get(0).getWindSpeed());
        assertEquals(expectedTemperature2, actual.get(1).getTemperature());
        assertEquals(expectedHumidity2, actual.get(1).getHumidity());
        assertEquals(expectedPrecipitation2, actual.get(1).getPrecipitation());
        assertEquals(expectedWindSpeed2, actual.get(1).getWindSpeed());
    }
}