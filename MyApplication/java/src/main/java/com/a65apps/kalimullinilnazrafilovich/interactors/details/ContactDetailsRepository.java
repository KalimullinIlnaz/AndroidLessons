package com.a65apps.kalimullinilnazrafilovich.interactors.details;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo;

import io.reactivex.rxjava3.core.Single;

public interface ContactDetailsRepository {
    Single<ContactDetailsInfo> getDetailsContact(String id);
}
