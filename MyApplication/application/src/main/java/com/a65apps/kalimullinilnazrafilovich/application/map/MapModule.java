package com.a65apps.kalimullinilnazrafilovich.application.map;


import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.application.scope.MapScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.ContactLocationModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.location.LocationRepository;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.MapPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @MapScope
    @NonNull
    public MapPresenter provideMapPresenter(@NonNull ContactLocationInteractor contactLocationModel,
                                            @NonNull ContactDetailsInteractor contactDetailsModel) {
        return new MapPresenter(contactLocationModel, contactDetailsModel);
    }

    @Provides
    @MapScope
    @NonNull
    public ContactLocationInteractor provideContactLocationModel(
            @NonNull LocationRepository contactLocationRepository) {
        return new ContactLocationModel(contactLocationRepository);
    }

    @Provides
    @MapScope
    @NonNull
    public ContactDetailsInteractor provideContactDetailsModel(
            @NonNull ContactDetailsRepository contactDetailsContentResolverRepository) {
        return new ContactDetailsModel(contactDetailsContentResolverRepository);
    }
}
