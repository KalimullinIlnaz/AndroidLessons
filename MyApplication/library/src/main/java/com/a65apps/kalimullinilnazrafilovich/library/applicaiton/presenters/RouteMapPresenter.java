package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Point;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.RouteMapView;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class RouteMapPresenter extends MvpPresenter<RouteMapView> {
    private final transient ContactLocationInteractor contactLocationInteractor;
    private final transient RouteInteractor routeInteractor;
    private final transient CompositeDisposable compositeDisposable;

    public RouteMapPresenter(@NonNull ContactLocationInteractor contactLocationInteractor,
                             @NonNull RouteInteractor routeInteractor) {
        compositeDisposable = new CompositeDisposable();

        this.contactLocationInteractor = contactLocationInteractor;
        this.routeInteractor = routeInteractor;
    }

    public void showMarkers() {
        compositeDisposable
                .add(contactLocationInteractor.loadAllContactLocations()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (list) -> getViewState().showMarkers(list),
                                Throwable::printStackTrace
                        ));
    }

    public void showRoute(@NonNull Point from, @NonNull Point to) {
        compositeDisposable
                .add(routeInteractor.loadRoute(from, to)
                        .subscribeOn(Schedulers.io())
                        .map((route) -> getListLatLng(routeInteractor.routeToPoints(route)))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                (dots) -> {
                                    if (dots.isEmpty()) {
                                        getViewState().showMessageNoRoute();
                                    } else {
                                        getViewState().showRoute(dots);
                                    }
                                },
                                Throwable::printStackTrace
                        )
                );
    }

    @NonNull
    private List<LatLng> getListLatLng(@NonNull List<Point> points) {
        if (points.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<LatLng> latLngs = new ArrayList<>();
            for (Point point : points) {
                latLngs.add(new LatLng(point.getLatitude(), point.getLongitude()));
            }
            return latLngs;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
