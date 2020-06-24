package com.a65apps.kalimullinilnazrafilovich.interactors.contacts;

import java.util.List;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import io.reactivex.rxjava3.core.Single;

public interface ContactListInteractor {

    Single<List<Contact>> loadContactsOnRequest(String query);

}
