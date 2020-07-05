package com.a65apps.kalimullinilnazrafilovich.interactors.contacts;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ContactListModel implements ContactListInteractor {
    private final transient ContactListRepository contactListRepository;

    public ContactListModel(ContactListRepository contactListRepository) {
        this.contactListRepository = contactListRepository;
    }

    @Override
    public Single<List<ContactShortInfo>> loadContactsOnRequest(String query) {
        return contactListRepository.getContactsOnRequest(query);
    }
}
