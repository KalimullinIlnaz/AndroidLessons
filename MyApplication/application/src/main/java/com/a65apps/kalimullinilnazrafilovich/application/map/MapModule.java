package com.a65apps.kalimullinilnazrafilovich.application.map;



import com.a65apps.kalimullinilnazrafilovich.application.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationModel;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapPresenter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDataBaseLocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactDetailsContentResolverRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.repositories.ContactLocationRepository;


import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(ContactLocationModel contactLocationModel, ContactDetailsModel contactDetailsModel){
        return new MapPresenter(contactLocationModel, contactDetailsModel);
    }

    @Provides
    @MapScope
    public ContactLocationModel provideContactLocationModel(ContactLocationRepository contactLocationRepository){
        return new ContactLocationModel(contactLocationRepository);
    }

    @Provides 
    @MapScope
    public ContactDetailsModel  provideContactDetailsModel(ContactDetailsContentResolverRepository contactDetailsContentResolverRepository,
                                                           ContactDataBaseLocationRepository contactDataBaseLocationRepository){
        return new ContactDetailsModel(contactDetailsContentResolverRepository, contactDataBaseLocationRepository);
    }

}
