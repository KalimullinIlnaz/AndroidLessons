package com.a65apps.kalimullinilnazrafilovich.application.app;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.route.GeocodeRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.db.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsContentResolverAndDBRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactListContentResolverRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.GeocodeRouteRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.NotificationAlarmManagerRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public ContactDetailsRepository provideContactDetailsContentResolverRepository(Context context, AppDatabase database) {
        return new ContactDetailsContentResolverAndDBRepository(context, database);
    }

    @Provides
    @Singleton
    public LocationRepository provideContactLocationRepository(AppDatabase database,
                                                               Context context,
                                                               ContactDetailsRepository contactDetailsContentResolverRepository){
        return new ContactLocationRepository(database, context, contactDetailsContentResolverRepository);
    }

    @Provides
    @Singleton
    public ContactListRepository provideContactListContentResolverRepository(Context context){
        return new ContactListContentResolverRepository(context);
    }

    @Provides
    @Singleton
    public GeocodeRepository provideGeocodeRouteRepository(Context context){
        return new GeocodeRouteRepository(context);
    }

    @Provides
    @Singleton
    public NotificationRepository provideNotificationRepository(Context context){
        return new NotificationAlarmManagerRepository(context);
    }
}
