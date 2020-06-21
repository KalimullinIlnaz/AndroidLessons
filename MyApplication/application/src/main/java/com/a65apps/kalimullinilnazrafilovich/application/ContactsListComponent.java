package com.a65apps.kalimullinilnazrafilovich.application;


import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.contacts.ContactsListModule;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ContactsListContainer;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.ContactsListScope;

import dagger.Subcomponent;

@ContactsListScope
@Subcomponent(modules = {ContactsListModule.class})
public interface ContactsListComponent extends ContactsListContainer {
}
