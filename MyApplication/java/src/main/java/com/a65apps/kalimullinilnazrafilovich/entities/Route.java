package com.a65apps.kalimullinilnazrafilovich.entities;

import java.util.List;

public class Route {
    private List<Point> points;

    public Route(List<Point> points){
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
