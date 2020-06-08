package com.a65apps.kalimullinilnazrafilovich.myapplication.views;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Location;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface FullMapView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMarkers(List<Location> locations);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRoute(List<LatLng> points);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMessageNoRoute();
}
