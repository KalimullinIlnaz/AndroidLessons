package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ContactDetailsPresenter @Inject constructor(
        private val notificationInteractor: NotificationInteractor,
        private val contactDetailsInteractor: ContactDetailsInteractor)
    : MvpPresenter<ContactDetailsView>() {

    fun showDetails(id: String) {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                contactDetailsInteractor.loadDetailsContact(id)
                        .flowOn(Dispatchers.IO)
                        .collect { contact ->
                            viewState?.showContactDetail(contact)
                        }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
        }
    }

    fun setNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.onBirthdayNotification(contactDetailsInfo)
    }

    fun removeNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.offBirthdayNotification(contactDetailsInfo)
    }

    fun getActualStateBirthdayNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.getNotificationWorkStatus(contactDetailsInfo)
    }

}