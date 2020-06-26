package com.a65apps.kalimullinilnazrafilovich.application.app;


import com.a65apps.kalimullinilnazrafilovich.application.contactDetails.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.application.map.ContactMapComponent;
import com.a65apps.kalimullinilnazrafilovich.application.contacts.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.application.routeMap.MapRouteComponent;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.AppContainer;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AppModule.class,
        DataBaseModule.class,
        RepositoryModule.class})
public interface AppComponent extends AppContainer {
    ContactsListComponent plusContactListContainer();
    ContactDetailsComponent plusContactDetailsContainer();
    ContactMapComponent plusContactMapContainer();
    MapRouteComponent plusMapRouteContainer();
}
