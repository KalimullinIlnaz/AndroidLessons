package com.a65apps.kalimullinilnazrafilovich.myapplication.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import Entities.Location;

public interface FullMapView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMarkers(List<Location> locations);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRoute(List<LatLng> points);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessageNoRoute();
}
