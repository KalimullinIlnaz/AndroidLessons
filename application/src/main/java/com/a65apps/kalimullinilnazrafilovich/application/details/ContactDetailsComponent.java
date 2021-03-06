package com.a65apps.kalimullinilnazrafilovich.application.details;

import com.a65apps.kalimullinilnazrafilovich.application.notification.BirthdayNotificationModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;

import dagger.Subcomponent;

@Subcomponent(modules = {
        ContactDetailsModule.class,
        BirthdayNotificationModule.class})
public interface ContactDetailsComponent extends ContactDetailsContainer {

}
