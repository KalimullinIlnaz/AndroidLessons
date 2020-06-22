package com.a65apps.kalimullinilnazrafilovich.application.contactDetails;

import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDataBaseLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsContentResolverRepository;

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    public ContactDetailsPresenter provideContactDetailsPresenter(ContactDetailsModel contactDetailsModel){
        return new ContactDetailsPresenter(contactDetailsModel);
    }

    @Provides
    @ContactDetailsScope
    public ContactDetailsModel provideContactDetailsModel(ContactDetailsContentResolverRepository contactDetailsRepository,
                                                          ContactDataBaseLocationRepository contactDataBaseLocationRepository){
        return new ContactDetailsModel(contactDetailsRepository,contactDataBaseLocationRepository);
    }

}

