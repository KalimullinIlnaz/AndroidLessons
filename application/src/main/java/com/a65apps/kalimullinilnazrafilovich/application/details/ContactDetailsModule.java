package com.a65apps.kalimullinilnazrafilovich.application.details;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {
    @Provides
    @NonNull
    public ContactDetailsInteractor provideContactDetailsInteractor(
            @NonNull ContactDetailsRepository contactDetailsRepository) {
        return new ContactDetailsModel(contactDetailsRepository);
    }
}
