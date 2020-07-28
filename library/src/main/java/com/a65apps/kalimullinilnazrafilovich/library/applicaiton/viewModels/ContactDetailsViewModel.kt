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

    fun setBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) = run {
        setReminder(contactDetailsInfo)
        birthdayNotification
    }

    private fun setReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.onBirthdayNotification(contactDetailsInfo)
    }

    fun removeBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) = run {
        removeReminder(contactDetailsInfo)
        birthdayNotification
    }

    private fun removeReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.offBirthdayNotification(contactDetailsInfo)
    }

    fun getBirthdayNotification(contactDetailsInfo: ContactDetailsInfo) = run {
        getStatusReminder(contactDetailsInfo)
        birthdayNotification
    }

    private fun getStatusReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification {
        return notificationInteractor.getNotificationWorkStatus(contactDetailsInfo)
    }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}
