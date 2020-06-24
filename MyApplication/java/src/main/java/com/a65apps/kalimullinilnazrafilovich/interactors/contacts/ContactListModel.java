package com.a65apps.kalimullinilnazrafilovich.interactors.contacts;

import java.util.List;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import io.reactivex.rxjava3.core.Single;

public class ContactListModel implements ContactListInteractor {
    private final ContactListRepository contactListRepository;

    public ContactListModel(ContactListRepository contactListRepository){
        this.contactListRepository = contactListRepository;
    }

    @Override
    public Single<List<Contact>> loadContactsOnRequest(String query) {
        return contactListRepository.getContactsOnRequest(query);
    }
}
