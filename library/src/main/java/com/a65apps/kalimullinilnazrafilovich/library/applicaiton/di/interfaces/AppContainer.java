package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces;


import androidx.annotation.NonNull;

public interface AppContainer {
    @NonNull
    ContactsListContainer plusContactListContainer();

    @NonNull ContactDetailsContainer plusContactDetailsContainer();

    @NonNull ContactMapContainer plusContactMapContainer();

    @NonNull MapRouteContainer plusMapRouteContainer();

    @NonNull BirthdayNotificationContainer plusBirthdayNotificationContainer();
}
