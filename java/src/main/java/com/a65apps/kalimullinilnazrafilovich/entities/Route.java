package com.a65apps.kalimullinilnazrafilovich.entities;

import java.util.List;

import io.reactivex.rxjava3.annotations.Nullable;

public class Route {
    @Nullable
    private final List<Point> points;

    public Route(@Nullable List<Point> points) {
        this.points = points;
    }

    @Nullable
    public List<Point> getPoints() {
        return points;
    }

}
