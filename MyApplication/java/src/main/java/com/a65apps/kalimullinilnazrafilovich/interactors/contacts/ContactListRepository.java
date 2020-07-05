package com.a65apps.kalimullinilnazrafilovich.interactors.contacts;


import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ContactListRepository {

    Single<List<ContactShortInfo>> getContactsOnRequest(String query);
}
