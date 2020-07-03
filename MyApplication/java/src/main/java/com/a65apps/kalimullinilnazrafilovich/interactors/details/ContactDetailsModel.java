package com.a65apps.kalimullinilnazrafilovich.interactors.details;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import io.reactivex.rxjava3.core.Single;

public class ContactDetailsModel implements ContactDetailsInteractor {
    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsModel(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public Single<Contact> loadDetailsContact(String id) {
        return contactDetailsRepository.getDetailsContact(id);
    }

}
