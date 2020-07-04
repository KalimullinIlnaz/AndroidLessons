package com.a65apps.kalimullinilnazrafilovich.application.contactDetails;

import androidx.annotation.NonNull;

import com.a65apps.kalimullinilnazrafilovich.application.scope.ContactDetailsScope;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsModel;
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsRepository;
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters.ContactDetailsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactDetailsModule {

    @Provides
    @ContactDetailsScope
    @NonNull
    public ContactDetailsInteractor provideContactDetailsInteractor(
            @NonNull ContactDetailsRepository contactDetailsRepository) {
        return new ContactDetailsModel(contactDetailsRepository);
    }

    @Provides
    @ContactDetailsScope
    @NonNull
    public ContactDetailsPresenter provideContactDetailsPresenter(
            @NonNull NotificationInteractor notificationInteractor,
            @NonNull ContactDetailsInteractor contactDetailsInteractor) {
        return new ContactDetailsPresenter(notificationInteractor, contactDetailsInteractor);
    }
}

