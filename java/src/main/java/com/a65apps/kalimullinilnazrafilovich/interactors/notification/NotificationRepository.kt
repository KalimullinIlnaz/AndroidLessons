package com.a65apps.kalimullinilnazrafilovich.interactors.notification

import com.a65apps.kalimullinilnazrafilovich.entities.BirthdayNotification
import com.a65apps.kalimullinilnazrafilovich.entities.ContactDetailsInfo
import java.util.*

interface NotificationRepository {
    fun setBirthdayReminder(contactDetailsInfo: ContactDetailsInfo,
                            gregorianCalendar: GregorianCalendar): BirthdayNotification

    fun removeBirthdayReminder(contactDetailsInfo: ContactDetailsInfo): BirthdayNotification

    fun getBirthdayNotificationEntity(contactDetailsInfo: ContactDetailsInfo,
                                      gregorianCalendar: GregorianCalendar?): BirthdayNotification


}