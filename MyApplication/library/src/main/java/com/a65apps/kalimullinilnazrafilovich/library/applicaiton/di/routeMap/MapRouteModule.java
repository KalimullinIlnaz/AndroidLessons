package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.routeMap;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.MapRouteScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapRoutePresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MapRouteModule {

    @Provides
    @MapRouteScope
    public MapRoutePresenter provideFullMapPresenter(DataBaseRepositoryImpl dataBaseRepository, GeocodeRepositoryImpl geocodeRepository){
        return new MapRoutePresenter(dataBaseRepository, geocodeRepository);
    }
}
