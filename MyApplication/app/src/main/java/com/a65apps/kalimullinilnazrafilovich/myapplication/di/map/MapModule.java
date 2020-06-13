package com.a65apps.kalimullinilnazrafilovich.myapplication.di.map;

import android.content.Context;

import com.a65apps.kalimullinilnazrafilovich.myapplication.di.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.myapplication.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.myapplication.repositories.ContactDetailsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public ContactDetailsRepository provideContactDetailsRepository(Context context){
        return new ContactDetailsRepository(context);
    }

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(Context context, ContactDetailsRepository contactDetailsRepository){
        return new MapPresenter(context, contactDetailsRepository);

    }
}
