package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepositoryImpl;

import javax.inject.Inject;

import Interactors.contacts.ContactListModel;
import Interactors.contacts.ContactListRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    public ContactListRepositoryImpl provideContactListRepository(Context context){
        return new ContactListRepositoryImpl(context);
    }

    @Provides
    @ContactsListScope
    public ContactListPresenter provideContactListPresenter(ContactListModel contactListModel){
        return new ContactListPresenter(contactListModel);
    }

    @Provides
    @ContactsListScope
    public ContactListModel provideContactListModel(ContactListRepositoryImpl contactListRepository){
        return new ContactListModel(contactListRepository);
    }
}
