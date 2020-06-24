package com.a65apps.kalimullinilnazrafilovich.application.contacts;


import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactsListScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactListPresenter;
import com.a65apps.kalimullinilnazrafilovich.interactors.contacts.ContactListModel;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactsListModule {

    @Provides
    @ContactsListScope
    public ContactListPresenter provideContactListPresenter(ContactListInteractor contactListModel){
        return new ContactListPresenter(contactListModel);
    }

    @Provides
    @ContactsListScope
    public ContactListInteractor provideContactListInteractor(ContactListRepository contactListRepository){
        return new ContactListModel(contactListRepository);
    }
}
