package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails;


import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepositoryImpl;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepositoryImpl;

import Interactors.db.DataBaseRepository;
import Interactors.details.ContactDetailsModel;
import Interactors.details.ContactDetailsRepository;
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
    public ContactDetailsModel provideContactDetailsModel(DataBaseRepositoryImpl dataBaseRepository){
        return new ContactDetailsModel(dataBaseRepository);
    }

}

