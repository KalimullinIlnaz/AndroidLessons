package com.a65apps.kalimullinilnazrafilovich.myapplication.di.app;

import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDatabase;
import com.a65apps.kalimullinilnazrafilovich.myapplication.app.AppDelegate;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails.ContactDetailsComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts.ContactsListComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.fullMap.FullMapComponent;
import com.a65apps.kalimullinilnazrafilovich.myapplication.di.map.MapComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,DataBaseModule.class})
public interface AppComponent {
    AppDatabase provideAppDatabase();

    ContactDetailsComponent plusContactDetailsComponent();
    ContactsListComponent plusContactsListComponent();
    MapComponent plusMapComponent();
    FullMapComponent plusFullMapComponent();

    void inject(AppDelegate appDelegate);
}
