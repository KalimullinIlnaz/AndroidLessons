package com.a65apps.kalimullinilnazrafilovich.application.app;


import com.a65apps.kalimullinilnazrafilovich.application.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.application.ContactMapComponent;
import com.a65apps.kalimullinilnazrafilovich.application.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.application.MapRouteComponent;
import com.a65apps.kalimullinilnazrafilovich.application.app.AppModule;
import com.a65apps.kalimullinilnazrafilovich.application.app.DataBaseModule;
import com.a65apps.kalimullinilnazrafilovich.application.app.RepositoryModule;
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
