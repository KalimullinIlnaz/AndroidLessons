package com.a65apps.kalimullinilnazrafilovich.application.notification;


import com.a65apps.kalimullinilnazrafilovich.application.details.ContactDetailsModule;
import com.a65apps.kalimullinilnazrafilovich.application.details.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.BirthdayNotificationContainer;

import dagger.Subcomponent;

@ContactDetailsScope
@Subcomponent(modules = {
        BirthdayNotificationModule.class,
        ContactDetailsModule.class})
public interface BirthdayNotificationComponent extends BirthdayNotificationContainer {

}
