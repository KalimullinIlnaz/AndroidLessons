package com.a65apps.kalimullinilnazrafilovich.interactors.details;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;

import io.reactivex.rxjava3.core.Single;

public class ContactDetailsModel implements ContactDetailsInteractor {
    private final transient ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsModel(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public Single<ContactDetailsInfo> loadDetailsContact(String id) {
        return contactDetailsRepository.getDetailsContact(id);
    }

}
