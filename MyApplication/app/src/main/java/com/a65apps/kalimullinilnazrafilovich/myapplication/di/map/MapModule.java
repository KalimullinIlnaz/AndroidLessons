package com.a65apps.kalimullinilnazrafilovich.myapplication.di.map;


import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepository;


import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        return new MapPresenter(dataBaseRepository, geocodeRepository);

    }
}
