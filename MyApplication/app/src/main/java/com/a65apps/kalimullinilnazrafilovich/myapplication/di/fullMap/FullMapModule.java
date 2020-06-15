package com.a65apps.kalimullinilnazrafilovich.myapplication.di.fullMap;



import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.FullMapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.FullMapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class FullMapModule {

    @Provides
    @FullMapScope
    public FullMapPresenter provideFullMapPresenter(DataBaseRepository dataBaseRepository, GeocodeRepository geocodeRepository){
        return new FullMapPresenter(dataBaseRepository, geocodeRepository);
    }
}
