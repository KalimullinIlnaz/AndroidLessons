package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepositoryImpl;

import javax.inject.Singleton;

import Interactors.geocode.GeocodeModel;
import Interactors.geocode.GeocodeRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GeocodeModule {

    @Provides
    @Singleton
    public GeocodeModel provideGeocodeModel(GeocodeRepositoryImpl geocodeRepository) {
        return new GeocodeModel(geocodeRepository);
    }
}
