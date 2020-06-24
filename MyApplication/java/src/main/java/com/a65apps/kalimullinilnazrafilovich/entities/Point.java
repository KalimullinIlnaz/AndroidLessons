package com.a65apps.kalimullinilnazrafilovich.entities;

import io.reactivex.rxjava3.annotations.Nullable;

public class Point {
    private final double latitude;
    private final double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    public double getLongitude() {
        return longitude;
    }

    @Nullable
    public double getLatitude() {
        return latitude;
    }

}
