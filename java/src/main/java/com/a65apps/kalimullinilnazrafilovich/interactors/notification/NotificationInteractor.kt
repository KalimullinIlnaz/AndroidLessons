package com.a65apps.kalimullinilnazrafilovich.interactors.notification

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo

interface NotificationInteractor {
    fun onBirthdayNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification

    fun offBirthdayNotification(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification

    fun getNotificationWorkStatus(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification
}
