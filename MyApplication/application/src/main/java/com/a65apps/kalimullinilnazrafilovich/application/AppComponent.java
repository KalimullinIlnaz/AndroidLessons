package com.a65apps.kalimullinilnazrafilovich.application;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.app.AppModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.app.DataBaseModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.app.RepositoryModule;
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
