package com.a65apps.kalimullinilnazrafilovich.interactors.route;

import com.a65apps.kalimullinilnazrafilovich.entities.Route;

import io.reactivex.rxjava3.core.Single;

public interface GeocodeRepository {
    Single<Route> getRoute(String from, String to);
}
