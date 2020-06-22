package com.a65apps.kalimullinilnazrafilovich.interactors.details;

import com.a65apps.kalimullinilnazrafilovich.entities.Contact;

import io.reactivex.rxjava3.core.Single;

public interface ContactDataBaseRepository {

    Single<Contact> getContactLocation(Single<Contact> contactEntitySingle);
}
