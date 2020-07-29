package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ContactDetailsViewModel @Inject constructor(
    private val notificationInteractor: NotificationInteractor,
    private val contactDetailsInteractor: ContactDetailsInteractor
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    private val contactDetails by lazy {
        MutableLiveData<ContactDetailsInfo>()
    }

    private val birthdayNotification by lazy {
        MutableLiveData<BirthdayNotification>()
    }

    fun getContactDetails(id: String) = run {
        loadDetails(id)
        contactDetails
    }

    private fun loadDetails(id: String) = try {
        launch {
            contactDetailsInteractor.loadDetailsContact(id)
                .flowOn(Dispatchers.IO)
                .collect { contact ->
                    contactDetails.value = contact
                }
        }
    } catch (e: Exception) {
        Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
    }

    fun setBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) =
        birthdayNotification.apply {
            value = setReminder(contactDetailsInfo)
        }

    private fun setReminder(contactDetailsInfo: ContactDetailsInfo) =
        notificationInteractor.onBirthdayNotification(contactDetailsInfo)

    fun removeBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) =
        birthdayNotification.apply {
            value = removeReminder(contactDetailsInfo)
        }

    private fun removeReminder(contactDetailsInfo: ContactDetailsInfo) =
        notificationInteractor.offBirthdayNotification(contactDetailsInfo)

    fun getBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) =
        birthdayNotification.apply {
            value = getStatusReminder(contactDetailsInfo)
        }

    private fun getStatusReminder(contactDetailsInfo: ContactDetailsInfo) =
        notificationInteractor.getNotificationWorkStatus(contactDetailsInfo)

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}
