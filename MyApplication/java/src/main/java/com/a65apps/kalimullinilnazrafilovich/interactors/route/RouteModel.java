package com.a65apps.kalimullinilnazrafilovich.interactors.route;

import com.a65apps.kalimullinilnazrafilovich.entities.Route;

import io.reactivex.rxjava3.core.Single;

public class RouteModel implements RouteInteractor {
    private final  GeocodeRepository geocodeRepository;

    public RouteModel(GeocodeRepository geocodeRepository){
        this.geocodeRepository = geocodeRepository;
    }

    @Override
    public Single<Route> loadRoute(String from, String to) {
        return geocodeRepository.getRoute(from, to);
    }
}
