package com.a65apps.kalimullinilnazrafilovich.application.contacts;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.contacts.ContactListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    @NonNull
    public ContactListPresenter provideContactListPresenter(@NonNull ContactListInteractor contactListModel) {
        return new ContactListPresenter(contactListModel);
    }

    @Provides
    @ContactsListScope
    @NonNull
    public ContactListInteractor provideContactListInteractor(@NonNull ContactListRepository contactListRepository) {
        return new ContactListModel(contactListRepository);
    }
}
