package com.a65apps.kalimullinilnazrafilovich.application;


import com.a65apps.kalimullinilnazrafilovich.application.contactDetails.ContactDetailsModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactDetailsContainer;
import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactDetailsScope;

import dagger.Subcomponent;

@ContactDetailsScope
@Subcomponent(modules = {ContactDetailsModule.class})
public interface ContactDetailsComponent extends ContactDetailsContainer {

}
