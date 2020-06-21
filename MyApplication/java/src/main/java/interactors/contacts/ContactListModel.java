package interactors.contacts;

import java.util.ArrayList;

import entities.Contact;

public class ContactListModel implements ContactListInteractor {
    private final ContactListRepository contactListRepository;

    public ContactListModel(ContactListRepository contactListRepository){
        this.contactListRepository = contactListRepository;
    }

    @Override
    public ArrayList<Contact> loadContactsOnRequest(String query) {
        return contactListRepository.getContactsOnRequest(query);
    }
}
