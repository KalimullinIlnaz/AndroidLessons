package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.contacts;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactListRepositoryImpl;

import interactors.contacts.ContactListModel;
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
