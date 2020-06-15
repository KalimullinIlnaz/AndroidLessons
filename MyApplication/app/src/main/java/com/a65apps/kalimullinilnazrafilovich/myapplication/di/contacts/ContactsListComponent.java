package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.fragments.ContactListFragment;

import dagger.Subcomponent;

@ContactsListScope
@Subcomponent(modules = {ContactsListModule.class})
public interface ContactsListComponent {
    void inject(ContactListFragment contactListFragment);
}
