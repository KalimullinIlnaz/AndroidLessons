package com.a65apps.kalimullinilnazrafilovich.interactors.route;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.entities.Route;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RouteModel implements RouteInteractor {
    private final  GeocodeRepository geocodeRepository;

    public RouteModel(GeocodeRepository geocodeRepository){
        this.geocodeRepository = geocodeRepository;
    }

    @Override
    public Single<Route> loadRoute(Point from, Point to) {
        return geocodeRepository.getRoute(from, to);
    }

    @Override
    public List<Point> routeToPoints(Route route) {
        return geocodeRepository.routeToPoints(route);
    }
}
