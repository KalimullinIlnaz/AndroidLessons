package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.routeMap.MapRouteComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.map.MapComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DataBaseModule.class,
        RepositoryModule.class,
        GeocodeModule.class})
public interface AppComponent {
    ContactDetailsComponent plusContactDetailsComponent();
    ContactsListComponent plusContactsListComponent();
    MapComponent plusMapComponent();
    MapRouteComponent plusFullMapComponent();
}
