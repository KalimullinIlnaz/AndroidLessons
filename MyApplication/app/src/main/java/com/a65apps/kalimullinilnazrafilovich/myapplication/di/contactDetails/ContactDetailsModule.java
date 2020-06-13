package com.a65apps.kalimullinilnazrafilovich.myapplication.di.contactDetails;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.ContactDetailsPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    public ContactDetailsRepository provideContactDetailsRepository(Context context){
        return new ContactDetailsRepository(context);
    }

    @Provides
    @ContactDetailsScope
    public ContactDetailsPresenter provideContactDetailsPresenter(Context context,ContactDetailsRepository contactDetailsRepository){
        return new ContactDetailsPresenter(context,contactDetailsRepository);
    }
}

