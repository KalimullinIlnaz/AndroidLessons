package Interactors.contacts;


import java.util.ArrayList;

import Entities.Contact;

public interface ContactListRepository {

    ArrayList<Contact> getContactsOnRequest(String query);
}
