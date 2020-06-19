package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepositoryImpl;

import javax.inject.Singleton;

import Interactors.details.ContactDetailsModel;
import Interactors.details.ContactDetailsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public ContactDetailsRepositoryImpl provideContactDetailsRepository(Context context) {
        return new ContactDetailsRepositoryImpl(context);
    }

    @Provides
    @Singleton
    public DataBaseRepositoryImpl provideDataBaseRepository(AppDatabase database, ContactDetailsRepositoryImpl contactDetailsRepository){
        return new DataBaseRepositoryImpl(database, contactDetailsRepository);
    }

    @Provides
    @Singleton
    public GeocodeRepositoryImpl provideGeocodeRepository(Context context){
        return new GeocodeRepositoryImpl(context);
    }
}
