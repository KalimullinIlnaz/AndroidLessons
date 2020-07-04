package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MapView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMapMarker(@NonNull LatLng coordinate);
}
