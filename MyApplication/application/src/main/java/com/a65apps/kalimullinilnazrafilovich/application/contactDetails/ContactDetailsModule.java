package com.a65apps.kalimullinilnazrafilovich.application.contactDetails;

import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    public ContactDetailsPresenter provideContactDetailsPresenter(ContactDetailsInteractor contactDetailsModel){
        return new ContactDetailsPresenter(contactDetailsModel);
    }

    @Provides
    @ContactDetailsScope
    public ContactDetailsInteractor provideContactDetailsInteractor(ContactDetailsRepository contactDetailsRepository){
        return new ContactDetailsModel(contactDetailsRepository);
    }

}

