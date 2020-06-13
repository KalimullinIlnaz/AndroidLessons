package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contacts;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactListRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    public ContactListRepository provideContactListRepository(Context context){
        return new ContactListRepository(context);
    }

    @Provides
    @ContactsListScope
    public ContactListPresenter provideContactListPresenter(ContactListRepository contactListRepository){
        return new ContactListPresenter(contactListRepository);
    }
}
