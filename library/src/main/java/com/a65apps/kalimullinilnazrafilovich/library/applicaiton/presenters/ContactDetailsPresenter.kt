package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import android.util.Log
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.views.ContactDetailsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@InjectViewState
class ContactDetailsPresenter @Inject constructor(
    private val notificationInteractor: NotificationInteractor,
    private val contactDetailsInteractor: ContactDetailsInteractor
) : MvpPresenter<ContactDetailsView>(), CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    fun showDetails(id: String) {
        try {
            launch {
                contactDetailsInteractor.loadDetailsContact(id)
                    .flowOn(Dispatchers.IO)
                    .collect { contact ->
                        viewState.showContactDetail(contact)
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

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}
