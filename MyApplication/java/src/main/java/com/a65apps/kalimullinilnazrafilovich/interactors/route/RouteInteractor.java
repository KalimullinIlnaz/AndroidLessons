package com.a65apps.kalimullinilnazrafilovich.interactors.route;

import com.a65apps.kalimullinilnazrafilovich.entities.Route;

import io.reactivex.rxjava3.core.Single;

public interface RouteInteractor {

    Single<Route> loadRoute(String from, String to);
}
