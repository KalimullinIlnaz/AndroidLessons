package interactors.details;

import entities.Contact;

public interface ContactDetailsRepository {

    Contact getDetailContact(String id);

}
