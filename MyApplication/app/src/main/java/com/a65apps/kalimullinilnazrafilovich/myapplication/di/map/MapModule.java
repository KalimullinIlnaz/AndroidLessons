package com.a65apps.kalimullinilnazrafilovich.myapplication.di.map;


import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepositoryImpl;

import Interactors.db.DataBaseModel;
import Interactors.db.DataBaseRepository;
import Interactors.geocode.GeocodeRepository;
import Interactors.location.MapLocationModel;
import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(MapLocationModel mapLocationModel, DataBaseModel dataBaseModel){
        return new MapPresenter(mapLocationModel, dataBaseModel);
    }

    @Provides
    @MapScope
    public MapLocationModel provideMapLocationModel(DataBaseRepositoryImpl dataBaseRepository, GeocodeRepositoryImpl geocodeRepository){
        return new MapLocationModel(dataBaseRepository, geocodeRepository);

    }
}
