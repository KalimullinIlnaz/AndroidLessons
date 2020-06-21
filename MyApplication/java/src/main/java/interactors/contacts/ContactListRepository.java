package interactors.contacts;


import java.util.ArrayList;

import entities.Contact;

public interface ContactListRepository {

    ArrayList<Contact> getContactsOnRequest(String query);
}
