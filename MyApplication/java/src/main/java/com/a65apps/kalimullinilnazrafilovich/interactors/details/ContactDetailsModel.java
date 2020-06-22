package com.a65apps.kalimullinilnazrafilovich.interactors.details;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import io.reactivex.rxjava3.core.Single;

public class ContactDetailsModel implements ContactDetailsInteractor {
    private final ContactDetailsRepository contactDetailsRepository;
    private final ContactDataBaseRepository contactDataBaseRepository;

    public ContactDetailsModel(ContactDetailsRepository contactDetailsRepository, ContactDataBaseRepository contactDataBaseRepository){
        this.contactDetailsRepository = contactDetailsRepository;
        this.contactDataBaseRepository = contactDataBaseRepository;
    }

    @Override
    public Single<Contact> loadDetailsContact(String id) {
        return contactDataBaseRepository.getContactLocation(contactDetailsRepository.getDetailsContact(id));
    }
}
