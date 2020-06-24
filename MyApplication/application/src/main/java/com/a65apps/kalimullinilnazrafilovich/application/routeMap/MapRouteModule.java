package com.a65apps.kalimullinilnazrafilovich.application.routeMap;

import com.a65apps.kalimullinilnazrafilovich.application.scope.MapRouteScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.RouteModel;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapRoutePresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRouteRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MapRouteModule {

    @Provides
    @MapRouteScope
    public MapRoutePresenter provideFullMapPresenter(ContactLocationInteractor contactLocationModel, RouteInteractor routeModel){
        return new MapRoutePresenter(contactLocationModel, routeModel);
    }

    @Provides
    @MapRouteScope
    public ContactLocationInteractor provideContactLocationModel(LocationRepository contactLocationRepository){
        return new ContactLocationModel(contactLocationRepository);
    }

    @Provides
    @MapRouteScope
    public RouteInteractor provideRouteModel(GeocodeRepository geocodeRouteRepository){
        return new RouteModel(geocodeRouteRepository);
    }
}