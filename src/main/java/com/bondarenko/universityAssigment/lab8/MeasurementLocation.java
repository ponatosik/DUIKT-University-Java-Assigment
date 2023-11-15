package com.bondarenko.universityAssigment.lab8;

import lombok.Value;

@Value
public class MeasurementLocation {
    int locationId;
    double latitude;
    double longitude;
    String name;
}
