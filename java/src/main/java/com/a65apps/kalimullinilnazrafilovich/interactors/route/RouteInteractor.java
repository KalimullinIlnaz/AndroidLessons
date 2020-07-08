package com.a65apps.kalimullinilnazrafilovich.interactors.route;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.entities.Route;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface RouteInteractor {

    Single<Route> loadRoute(Point from, Point to);

    List<Point> routeToPoints(Route route);
}
