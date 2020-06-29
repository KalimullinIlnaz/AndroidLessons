package com.a65apps.kalimullinilnazrafilovich.application.contactDetails;


import com.a65apps.kalimullinilnazrafilovich.application.notification.BirthdayNotificationModule;
import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;

import dagger.Subcomponent;

@ContactDetailsScope
@Subcomponent(modules = {ContactDetailsModule.class,
        BirthdayNotificationModule.class})
public interface ContactDetailsComponent extends ContactDetailsContainer {

}
