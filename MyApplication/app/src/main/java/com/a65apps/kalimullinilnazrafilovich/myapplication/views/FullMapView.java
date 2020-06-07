package com.a65apps.kalimullinilnazrafilovich.myapplication.views;

import com.a65apps.kalimullinilnazrafilovich.myapplication.models.Location;
import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface FullMapView extends MvpView {
    void showMarkers(List<Location> locations);
    void showRoute(String route,String status);
}
