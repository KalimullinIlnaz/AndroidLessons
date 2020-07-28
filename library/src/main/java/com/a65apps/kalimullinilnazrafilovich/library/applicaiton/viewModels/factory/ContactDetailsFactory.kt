package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import javax.inject.Inject

class ContactDetailsFactory @Inject constructor(
    private val notificationInteractor: NotificationInteractor,
    private val contactDetailsInteractor: ContactDetailsInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            NotificationInteractor::class.java,
            ContactDetailsInteractor::class.java
        ).newInstance(notificationInteractor, contactDetailsInteractor)
    }
}
