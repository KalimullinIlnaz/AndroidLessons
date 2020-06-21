package interactors.contacts;

import java.util.ArrayList;

import entities.Contact;

public interface ContactListInteractor {

    ArrayList<Contact> loadContactsOnRequest(String query);

}
