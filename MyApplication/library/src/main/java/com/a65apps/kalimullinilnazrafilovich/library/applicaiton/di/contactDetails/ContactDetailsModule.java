package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.contactDetails;

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.DataBaseRepositoryImpl;

import interactors.details.ContactDetailsModel;
import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    public ContactDetailsPresenter provideContactDetailsPresenter(DataBaseRepositoryImpl dataBaseRepository){
        return new ContactDetailsPresenter(dataBaseRepository);
    }

    @Provides
    @ContactDetailsScope
    public ContactDetailsModel provideContactDetailsModel(ContactDetailsRepositoryImpl contactDetailsRepository){
        return new ContactDetailsModel(contactDetailsRepository);
    }

}

