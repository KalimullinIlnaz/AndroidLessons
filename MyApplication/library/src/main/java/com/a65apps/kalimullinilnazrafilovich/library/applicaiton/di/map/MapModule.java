package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.map;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(DataBaseRepositoryImpl dataBaseRepository, GeocodeRepositoryImpl geocodeRepository){
        return new MapPresenter(dataBaseRepository, geocodeRepository);
    }

}
