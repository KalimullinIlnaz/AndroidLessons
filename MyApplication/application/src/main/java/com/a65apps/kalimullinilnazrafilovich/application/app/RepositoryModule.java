package com.a65apps.kalimullinilnazrafilovich.application.app;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDataBaseLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsContentResolverRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactListContentResolverRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRouteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public ContactDetailsContentResolverRepository provideContactDetailsContentResolverRepository(Context context) {
        return new ContactDetailsContentResolverRepository(context);
    }

    @Provides
    @Singleton
    public ContactLocationRepository provideContactLocationRepository(AppDatabase database,
                                                                      Context context,
                                                                      ContactDetailsContentResolverRepository contactDetailsContentResolverRepository){
        return new ContactLocationRepository(database, context, contactDetailsContentResolverRepository);
    }

    @Provides
    @Singleton
    public ContactDataBaseLocationRepository provideContactDataBaseLocationRepository(AppDatabase database){
        return new ContactDataBaseLocationRepository(database);
    }

    @Provides
    @Singleton
    public ContactListContentResolverRepository provideContactListContentResolverRepository(Context context){
        return new ContactListContentResolverRepository(context);
    }

    @Provides
    @Singleton
    public GeocodeRouteRepository provideGeocodeRouteRepository(Context context){
        return new GeocodeRouteRepository(context);
    }


}
