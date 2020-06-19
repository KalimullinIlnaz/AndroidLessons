package Interactors.contacts;

import java.util.ArrayList;

import Entities.Contact;

public interface ContactListInteractor {

    ArrayList<Contact> loadContactsOnRequest(String query);

}
