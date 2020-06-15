package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.fullMap.FullMapComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.map.MapComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        DataBaseModule.class,
        RepositoryModule.class})
public interface AppComponent {
    ContactDetailsComponent plusContactDetailsComponent();
    ContactsListComponent plusContactsListComponent();
    MapComponent plusMapComponent();
    FullMapComponent plusFullMapComponent();
}
