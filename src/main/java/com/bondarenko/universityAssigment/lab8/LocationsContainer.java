package com.bondarenko.universityAssigment.lab8;

import java.util.*;
import java.util.stream.Stream;

public class LocationsContainer {
    private final Set<MeasurementLocation> locations;

    public LocationsContainer(List<MeasurementLocation> locations) {
        this.locations = new LinkedHashSet<>(locations);
    }

    public LocationsContainer() {
        this.locations = new LinkedHashSet<>();
    }

    public Stream<MeasurementLocation> getLocations(){
        return locations.stream();
    }

    public boolean registerLocation(MeasurementLocation location) {
        return locations.add(location);
    }

    public boolean forgetLocation(MeasurementLocation location) {
        return locations.remove(location);
    }

    public Optional<MeasurementLocation> getLocationById(int id) {
        return locations.stream().filter(location -> location.getLocationId() == id).findAny();
    }

    public Optional<MeasurementLocation> getLocationByCoordinates(double latitude, double longitude) {
        return getLocationByCoordinatesExact(latitude, longitude).or(() -> getLocationByCoordinatesClosest(latitude, longitude));
    }

    public Optional<MeasurementLocation> getLocationByCoordinatesExact(double latitude, double longitude) {
        return locations.stream()
                .filter(location -> location.getLatitude() == latitude && location.getLongitude() == longitude).findAny();
    }

    public Optional<MeasurementLocation> getLocationByCoordinatesClosest(double latitude, double longitude) {
        return locations.stream()
                .min((l1, l2) -> (int) Math.round
                        (calculateDistance(l1.getLatitude(), l1.getLongitude(), latitude, longitude)
                        -calculateDistance(l2.getLatitude(), l2.getLongitude(), latitude, longitude)));
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.hypot(Math.abs(y2 - y1), Math.abs(x2 - x1));
    }
}
