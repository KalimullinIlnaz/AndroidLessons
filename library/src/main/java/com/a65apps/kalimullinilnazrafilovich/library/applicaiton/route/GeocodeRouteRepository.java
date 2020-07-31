package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.route;

import android.content.Context;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.entities.Route;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GeocodeRouteRepository implements GeocodeRepository {
    private final Context context;

    public GeocodeRouteRepository(@NonNull Context context) {
        this.context = context;
    }

    @Override
    @NonNull
    public Single<Route> getRoute(@NonNull Point fromPoint, @NonNull Point toPoint) {
        String from = fromPoint.getLatitude() + "," + fromPoint.getLongitude();
        String to = toPoint.getLatitude() + "," + toPoint.getLongitude();
        return GoogleRouteService.getInstance()
                .getJSONApi()
                .getRoute(from,
                        to,
                        "walking",
                        context.getResources().getString(R.string.google_maps_key))
                .subscribeOn(Schedulers.io())
                .map((dto) -> new GoogleDTOMapper().transform(dto));
    }

    @Override
    @NonNull
    public List<Point> routeToPoints(@NonNull Route route) {
        if (route.getPoints().isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Point> points = new ArrayList<>();
            for (Point point : route.getPoints()) {
                points.add(new Point(point.getLatitude(), point.getLongitude()));
            }
            return points;
        }
    }

}
