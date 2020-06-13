package com.a65apps.kalimullinilnazrafilovich.myapplication.di.fullMap;


import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.FullMapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.FullMapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;


import dagger.Module;
import dagger.Provides;

@Module
public class FullMapModule {

    @Provides
    @FullMapScope
    public ContactDetailsRepository provideContactDetailsRepository(Context context){
        return new ContactDetailsRepository(context);
    }

    @Provides
    @FullMapScope
    public FullMapPresenter provideFullMapPresenter(Context context, ContactDetailsRepository contactDetailsRepository){
        return new FullMapPresenter(context, contactDetailsRepository);
    }
}
