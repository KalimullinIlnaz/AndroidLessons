package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.GeocodeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public ContactDetailsRepository provideContactDetailsRepository(Context context) {
        return new ContactDetailsRepository(context);
    }

    @Provides
    @Singleton
    public DataBaseRepository provideDataBaseRepository(AppDatabase database, ContactDetailsRepository contactDetailsRepository){
        return new DataBaseRepository(database, contactDetailsRepository);
    }

    @Provides
    @Singleton
    public GeocodeRepository provideGeocodeRepository(Context context){
        return new GeocodeRepository(context);
    }
}
