package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

public interface MapView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMapMarker(LatLng coordinate);
}
