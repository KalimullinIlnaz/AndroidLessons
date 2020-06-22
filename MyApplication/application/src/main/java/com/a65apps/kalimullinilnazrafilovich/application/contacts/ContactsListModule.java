package com.a65apps.kalimullinilnazrafilovich.application.contacts;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactListContentResolverRepository;

import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListModel;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    public ContactListPresenter provideContactListPresenter(ContactListModel contactListModel){
        return new ContactListPresenter(contactListModel);
    }

    @Provides
    @ContactsListScope
    public ContactListModel provideContactListModel(ContactListContentResolverRepository contactListRepository){
        return new ContactListModel(contactListRepository);
    }
}
