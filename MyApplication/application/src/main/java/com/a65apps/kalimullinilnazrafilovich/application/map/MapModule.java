package com.a65apps.kalimullinilnazrafilovich.application.map;


import com.a65apps.kalimullinilnazrafilovich.application.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactLocationRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(ContactLocationInteractor contactLocationModel, ContactDetailsInteractor contactDetailsModel){
        return new MapPresenter(contactLocationModel, contactDetailsModel);
    }

    @Provides
    @MapScope
    public ContactLocationInteractor provideContactLocationModel(LocationRepository contactLocationRepository){
        return new ContactLocationModel(contactLocationRepository);
    }

    @Provides 
    @MapScope
    public ContactDetailsInteractor provideContactDetailsModel(ContactDetailsRepository contactDetailsContentResolverRepository){
        return new ContactDetailsModel(contactDetailsContentResolverRepository);
    }

}
