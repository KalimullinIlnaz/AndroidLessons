package com.a65apps.kalimullinilnazrafilovich.application.app;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.application.contacts.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.application.details.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.application.map.ContactMapComponent;
import com.a65apps.kalimullinilnazrafilovich.application.notification.BirthdayNotificationComponent;
import com.a65apps.kalimullinilnazrafilovich.application.route.MapRouteComponent;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.AppContainer;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DataBaseModule.class,
        RepositoryModule.class,
        TimeModule.class})
public interface AppComponent extends AppContainer {
    @NonNull
    ContactsListComponent plusContactListContainer();

    @NonNull
    ContactDetailsComponent plusContactDetailsContainer();

    @NonNull
    ContactMapComponent plusContactMapContainer();

    @NonNull
    MapRouteComponent plusMapRouteContainer();

    @NonNull
    BirthdayNotificationComponent plusBirthdayNotificationContainer();
}
