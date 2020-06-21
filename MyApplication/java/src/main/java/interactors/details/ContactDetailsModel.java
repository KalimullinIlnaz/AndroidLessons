package interactors.details;

import entities.Contact;

public class ContactDetailsModel implements ContactDetailsInteractor {
    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsModel(ContactDetailsRepository contactDetailsRepository){
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public Contact loadDetailsContact(String id) {
        return contactDetailsRepository.getDetailContact(id);
    }
}
