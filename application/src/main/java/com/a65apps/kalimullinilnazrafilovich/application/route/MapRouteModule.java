package com.a65apps.kalimullinilnazrafilovich.application.route;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteModel;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.route.RouteMapPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MapRouteModule {

    @Provides
    @MapRouteScope
    @NonNull
    public RouteMapPresenter provideRouteMapPresenter(
            @NonNull ContactLocationInteractor contactLocationModel,
            @NonNull RouteInteractor routeModel) {
        return new RouteMapPresenter(contactLocationModel, routeModel);
    }

    @Provides
    @MapRouteScope
    @NonNull
    public ContactLocationInteractor provideContactLocationModel(
            @NonNull LocationRepository contactLocationRepository) {
        return new ContactLocationModel(contactLocationRepository);
    }

    @Provides
    @MapRouteScope
    @NonNull
    public RouteInteractor provideRouteModel(@NonNull GeocodeRepository geocodeRouteRepository) {
        return new RouteModel(geocodeRouteRepository);
    }
}
