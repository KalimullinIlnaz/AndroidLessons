package com.a65apps.kalimullinilnazrafilovich.myapplication.di.routeMap;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapRouteScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.MapRoutePresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepositoryImpl;

import Interactors.db.DataBaseModel;
import Interactors.db.DataBaseRepository;
import Interactors.geocode.GeocodeRepository;
import Interactors.route.MapRouteModel;
import dagger.Module;
import dagger.Provides;

@Module
public class MapRouteModule {

    @Provides
    @MapRouteScope
    public MapRoutePresenter provideFullMapPresenter(MapRouteModel mapRouteModel, DataBaseModel dataBaseModel){
        return new MapRoutePresenter(mapRouteModel,dataBaseModel);
    }

    @Provides
    @MapRouteScope
    public MapRouteModel provideMapRouteModel(DataBaseRepositoryImpl dataBaseRepository, GeocodeRepositoryImpl geocodeRepository){
        return new MapRouteModel(dataBaseRepository, geocodeRepository);
    }
}
