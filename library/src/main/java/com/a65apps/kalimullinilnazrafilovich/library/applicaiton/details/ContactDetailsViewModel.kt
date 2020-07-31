package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import com.a65apps.kalimullinilnazrafilovich.interactors.details.ContactDetailsInteractor
import com.a65apps.kalimullinilnazrafilovich.interactors.notification.NotificationInteractor
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.MyDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ContactDetailsViewModel @Inject constructor(
    private val dispatchers: MyDispatchers,
    private val notificationInteractor: NotificationInteractor,
    private val contactDetailsInteractor: ContactDetailsInteractor,
    private val id: String
) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext =
        SupervisorJob() + dispatchers.main()

    private val contact by lazy {
        MutableLiveData<ContactDetailsInfo>()
    }

    private val notification by lazy {
        MutableLiveData<BirthdayNotification>()
    }

    private val loadingStatus by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        loadDetails()
    }

    fun getContactDetails(): LiveData<ContactDetailsInfo> = contact

    private fun loadDetails() = try {
        launch {
            contactDetailsInteractor.loadDetailsContact(id)
                .flowOn(dispatchers.io())
                .collect { contactDetails ->
                    contact.value = contactDetails
                    loadingStatus.value = false
                }
        }
    } catch (e: Exception) {
        loadingStatus.value = false
        Log.e(this.javaClass.simpleName, e.printStackTrace().toString())
    }

    fun getLoadStatus() = loadingStatus

    fun setBirthdayNotification() =
        notification.apply {
            value = setReminder(contact.value)
        }

    private fun setReminder(contactDetailsInfo: ContactDetailsInfo?) =
        contactDetailsInfo?.let {
            notificationInteractor.onBirthdayNotification(it)
        }

    fun removeBirthdayNotification() =
        notification.apply {
            value = removeReminder(contact.value)
        }

    private fun removeReminder(contactDetailsInfo: ContactDetailsInfo?) =
        contactDetailsInfo?.let {
            notificationInteractor.offBirthdayNotification(it)
        }

    fun getBirthdayNotification() =
        notification.apply {
            value = getStatusReminder(contact.value)
        }

    private fun getStatusReminder(contactDetailsInfo: ContactDetailsInfo?) =
        contactDetailsInfo?.let {
            notificationInteractor.getNotificationWorkStatus(it)
        }

    override fun onCleared() {
        cancel()
        super.onCleared()
    }
}
