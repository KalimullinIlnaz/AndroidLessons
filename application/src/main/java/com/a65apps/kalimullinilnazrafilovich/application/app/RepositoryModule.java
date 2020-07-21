package com.a65apps.kalimullinilnazrafilovich.application.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;
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
    @NonNull
    public ContactDetailsRepository provideContactDetailsContentResolverRepository(
            @NonNull Context context,
            @NonNull AppDatabase db) {
        return new ContactDetailsContentResolverAndDBRepository(context, db);
    }

    @Provides
    @Singleton
    @NonNull
    public LocationRepository provideContactLocationRepository(
            @NonNull AppDatabase database,
            @NonNull Context context,
            @NonNull ContactDetailsRepository contactDetailsContentResolverRepository) {
        return new ContactLocationRepository(database,
                context,
                contactDetailsContentResolverRepository);
    }

    @Provides
    @Singleton
    @NonNull
    public ContactListRepository provideContactListContentResolverRepository(@NonNull Context context) {
        return new ContactListContentResolverRepository(context);
    }

    @Provides
    @Singleton
    @NonNull
    public GeocodeRepository provideGeocodeRouteRepository(@NonNull Context context) {
        return new GeocodeRouteRepository(context);
    }

    @Provides
    @Singleton
    @NonNull
    public NotificationRepository provideNotificationRepository(@NonNull Context context) {
        return new NotificationAlarmManagerRepository(context);
    }
}
