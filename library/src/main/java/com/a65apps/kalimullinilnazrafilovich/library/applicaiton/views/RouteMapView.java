package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.entities.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface RouteMapView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMarkers(@NonNull List<Location> locations);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRoute(@NonNull List<LatLng> points);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessageNoRoute();
}
