package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.app;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRepositoryImpl;

import javax.inject.Singleton;

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
