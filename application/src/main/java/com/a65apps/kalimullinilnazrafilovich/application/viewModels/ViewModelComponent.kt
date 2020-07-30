package com.a65apps.kalimullinilnazrafilovich.application.viewModels

import com.a65apps.kalimullinilnazrafilovich.application.details.ContactDetailsModule
import com.a65apps.kalimullinilnazrafilovich.application.notification.BirthdayNotificationModule
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ViewModelComponentContainer
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.ViewModelComponentFactory
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details.ContactDetailsViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@ViewModelScope
@Subcomponent(
    modules = [
        ContactDetailViewModelModule::class,
        BirthdayNotificationModule::class,
        ContactDetailsModule::class]
)
interface ViewModelComponent : ViewModelComponentContainer {
    override fun getViewModel(): ContactDetailsViewModel

    @Subcomponent.Factory
    interface Factory : ViewModelComponentFactory {
        override fun create(
            @BindsInstance id: String
        ): ViewModelComponent
    }
}
