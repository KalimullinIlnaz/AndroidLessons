package com.a65apps.kalimullinilnazrafilovich.application.viewModels

import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.MyDispatchers
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details.ContactDetailsViewModel
import dagger.Module
import dagger.Provides

@Module()
class ContactDetailViewModelModule {
    @ViewModelScope
    @Provides
    fun provideContactDetailsViewModel(
        dispatchers: MyDispatchers,
        notificationInteractor: NotificationInteractor,
        contactDetailsInteractor: ContactDetailsInteractor,
        id: String
    ) =
        ContactDetailsViewModel(
            dispatchers,
            notificationInteractor,
            contactDetailsInteractor,
            id
        )
}
