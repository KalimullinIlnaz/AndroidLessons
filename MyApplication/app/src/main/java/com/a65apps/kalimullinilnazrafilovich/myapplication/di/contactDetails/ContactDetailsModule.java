package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails;


import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.DataBaseRepository;


import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    public ContactDetailsPresenter provideContactDetailsPresenter(DataBaseRepository dataBaseRepository){
        return new ContactDetailsPresenter(dataBaseRepository);
    }
}

